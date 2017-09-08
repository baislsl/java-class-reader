package com.baislsl.decompiler.structure.constantPool;

public class IntegerTag extends FloatIntegerBasic {
    public IntegerTag(int tag) {
        super(tag);
    }

    public long getValue() {
        return Integer.toUnsignedLong(bytes);
    }
}
