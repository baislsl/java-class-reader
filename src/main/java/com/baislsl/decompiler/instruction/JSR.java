package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class JSR extends Instruction {
    @Override
    public void exec() throws DecompileException {
        // TODO:
        super.exec();
        throw new DecompileException("TODO: " + this.getClass().getSimpleName());
    }
}
