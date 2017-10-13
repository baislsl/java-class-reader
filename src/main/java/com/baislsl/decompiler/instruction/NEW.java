package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class NEW extends Instruction {
    @Override
    public void exec() throws DecompileException {
        // push an empty object reference into the stack
        opStack.push(new Value(""));
    }
}
