package com.baislsl.decompiler.structure.attribute;

/**
 * Used in CodeAttr
 */
public class ExceptionTable {
    public static final int PC_SIZE = 2;
    public static final int CATCH_TYPE_SIZE = 2;
    public int startPC, endPC;
    public int handlerPC;
    public int catchType;

    public ExceptionTable() {
    }

    public ExceptionTable(int startPC, int endPC, int handlerPC, int catchType) {
        this.startPC = startPC;
        this.endPC = endPC;
        this.handlerPC = handlerPC;
        this.catchType = catchType;
    }
}
