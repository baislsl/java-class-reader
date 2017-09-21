package com.baislsl.decompiler.instruction;

public class LDC_W extends ldcInstruction {
    @Override
    protected int getIndex() {
        return get2();
    }
}
