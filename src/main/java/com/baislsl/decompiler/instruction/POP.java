package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class POP extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.pop();
    }
}
