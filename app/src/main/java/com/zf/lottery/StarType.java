package com.zf.lottery;

public enum StarType {
    FirstThree("前三位"), LastThree("后三位"), FirstTwo("前二位"), LastTwo("后二位"), CombThree("组选后三"), CombTwo("组选后二");

    StarType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name = null;
}
