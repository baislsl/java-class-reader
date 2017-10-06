package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class DUP2 extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value value = opStack.pop();
        opStack.push(value);
        opStack.push(value);
    }
}
