package com.philia093.blocks.machines;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements SidedInventory, NamedScreenHandlerFactory {
    protected final DefaultedList<ItemStack> inventory;
    protected int progress = 0;
    protected int maxProgress = 100;
    protected Recipe<?> currentRecipe;

    protected final int inputSlotCount;
    protected final int outputSlotCount;
    protected final int totalSlots;

    public AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,
                                      int inputSlots, int outputSlots) {
        super(type, pos, state);
        this.inputSlotCount = inputSlots;
        this.outputSlotCount = outputSlots;
        this.totalSlots = inputSlots + outputSlots;
        this.inventory = DefaultedList.ofSize(totalSlots, ItemStack.EMPTY);
    }

    // 抽象方法 - 子类必须实现
    public abstract RecipeType<?> getRecipeType();
    public abstract int getProcessingTime();

    // 可重写的方法
    public boolean canProcess(Recipe<?> recipe) {
        if (recipe == null) return false;

        // 检查输入槽是否有足够物品
        for (int i = 0; i < inputSlotCount; i++) {
            if (getStack(i).isEmpty()) {
                return false;
            }
        }

        // 获取配方输出
        ItemStack output = getRecipeOutput(recipe);
        if (output.isEmpty()) return false;

        // 检查输出槽空间
        ItemStack currentOutput = getStack(inputSlotCount);
        if (currentOutput.isEmpty()) return true;
        if (!ItemStack.areEqual(currentOutput, output)) return false;
        return currentOutput.getCount() + output.getCount() <= currentOutput.getMaxCount();
    }

    public void processRecipe(Recipe<?> recipe) {
        // 消耗输入槽的物品
        for (int i = 0; i < inputSlotCount; i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                stack.decrement(1);
            }
        }

        // 生成输出
        ItemStack output = getRecipeOutput(recipe);
        ItemStack currentOutput = getStack(inputSlotCount);

        if (currentOutput.isEmpty()) {
            setStack(inputSlotCount, output.copy());
        } else {
            currentOutput.increment(output.getCount());
        }

        markDirty();
    }

    protected ItemStack getRecipeOutput(Recipe<?> recipe) {
        if (recipe == null) return ItemStack.EMPTY;

        // 如果 recipe 是 MachineRecipe 类型，使用自定义方法
        if (recipe instanceof MachineRecipe) {
            return ((MachineRecipe) recipe).getResult();
        }

        // 否则使用原版方法
        return recipe.getOutput(world != null ? world.getRegistryManager() : null);
    }

    // 静态 Tick 方法
    public static void tick(World world, BlockPos pos, BlockState state, AbstractMachineBlockEntity entity) {
        if (world.isClient) return;

        boolean wasActive = state.get(AbstractMachineBlock.ACTIVE);
        boolean isActive = false;

        // 查找配方
        Optional<Recipe<?>> optionalRecipe = findRecipe(entity);

        if (optionalRecipe.isPresent()) {
            Recipe<?> recipe = optionalRecipe.get();
            if (entity.canProcess(recipe)) {
                isActive = true;
                entity.progress++;
                entity.currentRecipe = recipe;

                if (entity.progress >= entity.getProcessingTime()) {
                    entity.processRecipe(recipe);
                    entity.progress = 0;
                    entity.currentRecipe = null;
                    entity.markDirty();
                }
            } else {
                if (entity.progress != 0) {
                    entity.progress = 0;
                    entity.markDirty();
                }
            }
        } else {
            if (entity.progress != 0) {
                entity.progress = 0;
                entity.markDirty();
            }
            entity.currentRecipe = null;
        }

        // 更新方块状态
        if (wasActive != isActive) {
            state = state.with(AbstractMachineBlock.ACTIVE, isActive);
            world.setBlockState(pos, state, 3);
        }
    }

    @SuppressWarnings("unchecked")
    protected static Optional<Recipe<?>> findRecipe(AbstractMachineBlockEntity entity) {
        if (entity.world == null) return Optional.empty();

        RecipeManager recipeManager = ((ServerWorld) entity.world).getRecipeManager();

        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Recipe<Inventory>> recipes = (List) recipeManager.getAllMatches(
                (RecipeType) entity.getRecipeType(),
                entity,
                entity.world
        );

        return recipes.isEmpty() ? Optional.empty() : Optional.of(recipes.get(0));
    }

    // 掉落物品
    public void dropInventory(World world, BlockPos pos) {
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                net.minecraft.util.ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    // ========== Inventory 接口实现 ==========
    @Override
    public int size() {
        return totalSlots;
    }

    @Override
    public boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot < 0 || slot >= totalSlots) return ItemStack.EMPTY;
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(inventory, slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    @Override
    public void clear() {
        inventory.clear();
        markDirty();
    }

    // ========== SidedInventory 接口实现 ==========
    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[totalSlots];
        for (int i = 0; i < totalSlots; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot < inputSlotCount;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot >= inputSlotCount;
    }

    // ========== NBT 持久化 ==========
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("Progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("Progress");
    }

    // ========== Getters ==========
    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getInputSlotCount() {
        return inputSlotCount;
    }

    public int getOutputSlotCount() {
        return outputSlotCount;
    }

    @Override
    public Text getDisplayName() {
        if (world != null && world.getBlockState(pos).getBlock() instanceof AbstractMachineBlock) {
            return ((AbstractMachineBlock) world.getBlockState(pos).getBlock()).getDisplayName();
        }
        return Text.literal("Machine");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        // 需要返回正确的 ScreenHandler
        // 这个方法需要在子类中实现，或者使用泛型
        return null; // 子类应该重写此方法
    }
}