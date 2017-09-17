package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class ARETURN extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        result.append("return ")
              .append(opStack.pop())
              .append(";\n");
    }
}
