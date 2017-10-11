package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class SWAP extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value value1 = opStack.pop();
        Value value2 = opStack.pop();
        opStack.push(value1);
        opStack.push(value2);
    }
}
