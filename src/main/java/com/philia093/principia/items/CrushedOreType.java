package com.philia093.principia.items;

public enum CrushedOreType {
    MAGNETITE("crushed_magnetite", 0x32323A, "Fe₃O₄"),
    NATIVE_COPPER("crushed_native_copper", 0xAA5F37, "Cu"),
    MALACHITE("crushed_malachite", 0x378246, "Cu₂CO₃(OH)₂"),
    GALENA("crushed_galena", 0x5F646E, "PbS"),
    CASSITERITE("crushed_cassiterite", 0x46372D, "SnO₂"),
    SPHALERITE("crushed_sphalerite", 0x825F2D, "(Zn,Fe)S"),
    VANADINITE("crushed_vanadinite", 0xB45028, "Pb₅(VO₄)₃Cl"),
    PENTLANDITE("crushed_pentlandite", 0x9B8237, "(Fe,Ni)₉S₈"),
    BAUXITE("crushed_bauxite", 0x735037, "Al(OH)₃/AlOOH"),
    ILMENITE("crushed_ilmenite", 0x3C3741, "FeTiO₃"),
    CHROMITE("crushed_chromite", 0x3C413C, "FeCr₂O₄"),
    PYROLUSITE("crushed_pyrolusite", 0x464B55, "MnO₂"),
    CHALCOPYRITE("crushed_chalcopyrite", 0xA58737, "CuFeS₂"),
    PITCHBLENDE("crushed_pitchblende", 0x32373C, "UO₂/U₃O₈"),
    MONAZITE("crushed_monazite", 0xA06E46, "(Ce,La,Nd,Th)PO₄"),
    BERYL("crushed_beryl", 0x7DA08C, "Be₃Al₂Si₆O₁₈"),
    MOLYBDENITE("crushed_molybdenite", 0x555A5F, "MoS₂"),
    BISMUTHINITE("crushed_bismuthinite", 0x8C96A0, "Bi₂S₃");

    public final String name;
    public final int color;
    public final String formula;

    CrushedOreType(String name, int color, String formula) {
        this.name = name;
        this.color = color;
        this.formula = formula;
    }
}