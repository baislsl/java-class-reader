package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class AASTORE extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value value = opStack.pop();
        Value index = opStack.pop();
        Value arrayRef = opStack.pop();

        result.append(arrayRef)
                .append("[")
                .append(index)
                .append("] = ")
                .append(value)
                .append(";\n");
    }
}
