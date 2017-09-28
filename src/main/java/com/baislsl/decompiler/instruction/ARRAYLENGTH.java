package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class ARRAYLENGTH extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value arrayRef = opStack.pop();
        opStack.push(
                new Value(arrayRef + ".length")
        );
    }
}
