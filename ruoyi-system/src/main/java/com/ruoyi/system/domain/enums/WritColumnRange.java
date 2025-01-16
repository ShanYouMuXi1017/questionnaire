package com.ruoyi.system.domain.enums;

public enum WritColumnRange {
    RANGE1(4, 6, 7),
    RANGE2(8, 14, 15),
    RANGE3(16, 20, 21),
    RANGE4(22, 26, 27),
    RANGE5(28, 34, 35);

    private final int startCol;
    private final int endCol;
    private final int aimCol;

    // 构造方法
    WritColumnRange(int startCol, int endCol, int aimCol) {
        this.startCol = startCol;
        this.endCol = endCol;
        this.aimCol = aimCol;
    }

    // Getter 方法
    public int getStartCol() {
        return startCol;
    }

    public int getEndCol() {
        return endCol;
    }

    public int getAimCol() {
        return aimCol;
    }
}

