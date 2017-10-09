package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * dcmpg, dcmpl
 */
public abstract class CompareInstruction extends Instruction {
    // TODO: how to decompile sign(a - b)

    protected String operator;

    @Override
    public void exec() throws DecompileException {
        super.exec();

        opStack.push(
                new Value(opStack.pop() + operator + opStack.pop())
        );
    }
}
