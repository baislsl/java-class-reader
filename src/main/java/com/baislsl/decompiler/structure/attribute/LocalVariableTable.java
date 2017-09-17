package com.baislsl.decompiler.structure.attribute;

public class LocalVariableTable {
    public static final int START_PC_SIZE = 2;
    public static final int LENGTH_SIZE = 2;
    public static final int INDEX_SIZE = 2;

    public int startPC;
    public int length;
    public int nameIndex;
    public int descriptorIndex;
    public int index;
}
