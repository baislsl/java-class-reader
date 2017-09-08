package com.baislsl.decompiler.constantPool;

public class FLoatTag extends FloatIntegerBasic {
    public FLoatTag(int tag) {
        super(tag);
    }

    public float getValue() {
        return (float) bytes;
    }
}
