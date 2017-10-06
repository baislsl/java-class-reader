package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * +, -, *, / for i, f, d
 */
public abstract class OperationInstuction extends Instruction {
    protected String operator;
    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(
                new Value("(" + opStack.pop() + ") " + operator + " (" + opStack.pop() + ")")
        );
    }
}
