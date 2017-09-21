package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class I2D extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(
                new Value("(double)(" + opStack.pop() + ")")
        );

    }
}
