package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * aaload, baload
 */
public abstract class RefLoadInstruction extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();

        Value index = opStack.pop();
        Value arrayRef = opStack.pop();

        opStack.push(
                new Value(arrayRef.toString() + "[" + index.toString() + "]")
        );
    }
}
