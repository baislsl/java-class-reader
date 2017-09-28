package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class ATHROW extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();

        Value objectRef = opStack.pop();
        result.append("throw ")
                .append(objectRef)
                .append(";\n");

    }
}
