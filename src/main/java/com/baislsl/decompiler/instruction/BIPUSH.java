package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

import java.util.Stack;

public class BIPUSH extends Instruction {

    @Override
    public void exec() throws DecompileException {
        super.exec();

        opStack.push((int) bytes[1]);

    }
}
