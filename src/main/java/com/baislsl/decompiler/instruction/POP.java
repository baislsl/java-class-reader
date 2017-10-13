package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class POP extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value value = opStack.pop();
        if (!value.used) {
            result.append(value).append(";\n");
        }
    }
}
