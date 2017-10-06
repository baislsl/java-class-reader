package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * d2f, d2i, d2l, i2d
 */
public abstract class ConvertTypeInstuction extends Instruction{
    protected String convertType;

    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(
                new Value("((" + convertType + ")(" + opStack.pop() + "))")
        );
    }
}
