package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class ALOAD_0 extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(
            localVariableTables.get(0).value()
        );
    }
}
