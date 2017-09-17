package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class DMUL extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(
                new Value(opStack.pop() + "*" + opStack.pop())
        );
    }
}
