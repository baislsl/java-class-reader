package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class DLOAD_2 extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(localVariableTables.get(2).value());
    }
}
