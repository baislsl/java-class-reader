package com.baislsl.decompiler.constantPool;

public class IntegerTag extends FloatIntegerBasic {
    public IntegerTag(int tag) {
        super(tag);
    }

    public long getValue() {
        return Integer.toUnsignedLong(bytes);
    }
}
