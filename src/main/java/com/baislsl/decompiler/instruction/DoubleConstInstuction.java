package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * dconst_0, dconst_1
 */
public abstract class DoubleConstInstuction extends Instruction {
    protected String value;

    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(new Value(value));
    }
}
