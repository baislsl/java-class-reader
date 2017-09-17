package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class IMUL extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value value1 = opStack.pop();
        Value value2 = opStack.pop();

        opStack.push(
            new Value(value1.toString() + "*" + value2.toString())
        );
    }
}
