package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class MONITOREXIT extends Instruction {
    @Override
    public void exec() throws DecompileException {
        // TODO:
        super.exec();
        throw new DecompileException("TODO: " + this.getClass().getSimpleName());
    }
}
