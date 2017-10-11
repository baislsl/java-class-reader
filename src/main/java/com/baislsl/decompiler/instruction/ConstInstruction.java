package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * dconst_0, dconst_1..
 * fconst_0, fconst_1..
 * sipush
 * ...
 */
public abstract class ConstInstruction extends Instruction {
    protected String value;

    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(new Value(value));
    }
}
