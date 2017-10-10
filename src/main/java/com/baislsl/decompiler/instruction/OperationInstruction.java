package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * +, -, *, / for i, f, d
 */
public abstract class OperationInstruction extends Instruction {
    protected String operator;
    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value value2 = opStack.pop();
        Value value1 = opStack.pop();
        opStack.push(
                new Value("(" + value1 + ") " + operator + " (" + value2 + ")")
        );
    }
}
