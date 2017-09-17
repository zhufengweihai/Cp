package com.zf.lottery;

public enum StarType {
    FirstThree("前后三"), LastThree("后三位"), FirstTwo("前后二"), LastTwo("后二位"), GroupThree("三星组三"), GroupSix("三星组六"), CombTwo("组选后二"), LastOne("单选后一");

    StarType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name = null;
}
