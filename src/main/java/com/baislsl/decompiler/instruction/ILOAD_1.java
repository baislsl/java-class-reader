package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class ILOAD_1 extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();

        opStack.push(localVariableTables.get(1).value());
    }
}
