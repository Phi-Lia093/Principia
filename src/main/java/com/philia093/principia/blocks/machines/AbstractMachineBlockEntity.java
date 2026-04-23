package com.philia093.principia.blocks.machines;

import com.philia093.principia.recipes.MachineRecipe;
import com.philia093.principia.registry.ModRecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
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
    protected Recipe<?> currentRecipe;

    protected final PropertyDelegate propertyDelegate;

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

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> getCurrentMaxProgress();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public abstract RecipeType<?> getRecipeType();

    public int getCurrentMaxProgress() {
        if (currentRecipe instanceof MachineRecipe) {
            return ((MachineRecipe) currentRecipe).getProcessingTime();
        }
        return 100;
    }

    protected boolean hasEnoughPower(int amount) {
        return true;
    }

    protected void consumePower(int amount) {
    }

    public boolean canProcess(Recipe<?> recipe) {
        if (!(recipe instanceof MachineRecipe machineRecipe)) return false;

        List<Ingredient> inputs = machineRecipe.getInputs();

        for (int i = 0; i < inputs.size(); i++) {
            if (i >= inputSlotCount) return false;
            ItemStack stack = getStack(i);
            if (!inputs.get(i).test(stack)) {
                return false;
            }
        }

        List<ItemStack> outputs = machineRecipe.getAllOutputs();
        for (int i = 0; i < outputs.size(); i++) {
            if (i >= outputSlotCount) return false;
            int slot = inputSlotCount + i;
            ItemStack currentOutput = getStack(slot);
            ItemStack output = outputs.get(i);

            if (!currentOutput.isEmpty()) {
                if (!ItemStack.areItemsEqual(currentOutput, output)) return false;
                if (currentOutput.getCount() + output.getCount() > currentOutput.getMaxCount()) return false;
            }
        }

        return hasEnoughPower(machineRecipe.getPowerRequired());
    }

    public void processRecipe(Recipe<?> recipe) {
        if (!(recipe instanceof MachineRecipe machineRecipe)) return;

        List<Ingredient> inputs = machineRecipe.getInputs();
        for (int i = 0; i < inputs.size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                stack.decrement(1);
            }
        }

        List<ItemStack> outputs = machineRecipe.getAllOutputs();
        for (int i = 0; i < outputs.size(); i++) {
            int slot = inputSlotCount + i;
            ItemStack output = outputs.get(i).copy();
            ItemStack currentOutput = getStack(slot);

            if (currentOutput.isEmpty()) {
                setStack(slot, output);
            } else {
                currentOutput.increment(output.getCount());
            }
        }

        consumePower(machineRecipe.getPowerRequired());

        markDirty();
    }

    public static void tick(World world, BlockPos pos, BlockState state, AbstractMachineBlockEntity entity) {
        if (world.isClient) return;

        boolean wasActive = state.get(AbstractMachineBlock.ACTIVE);
        boolean isActive = false;

        Optional<MachineRecipe> optionalRecipe = findRecipe(entity);

        if (optionalRecipe.isPresent()) {
            MachineRecipe recipe = optionalRecipe.get();
            if (entity.canProcess(recipe)) {
                isActive = true;
                entity.progress++;
                entity.currentRecipe = recipe;

                if (entity.progress >= recipe.getProcessingTime()) {
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

        if (wasActive != isActive) {
            state = state.with(AbstractMachineBlock.ACTIVE, isActive);
            world.setBlockState(pos, state, 3);
        }
    }

    protected static Optional<MachineRecipe> findRecipe(AbstractMachineBlockEntity entity) {
        if (entity.world == null) return Optional.empty();

        RecipeManager recipeManager = entity.world.getRecipeManager();

        List<MachineRecipe> recipes = recipeManager.getAllMatches(
                ModRecipeTypes.MACHINE,
                entity,
                entity.world
        );

        return recipes.isEmpty() ? Optional.empty() : Optional.of(recipes.get(0));
    }

    public void dropInventory(World world, BlockPos pos) {
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                net.minecraft.util.ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

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

    public int getProgress() {
        return progress;
    }

    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
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
        return null;
    }
}