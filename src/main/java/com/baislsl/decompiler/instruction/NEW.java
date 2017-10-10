package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class NEW extends Instruction {
    // TODO
    @Override
    public void exec() throws DecompileException {
        System.out.println("use instruction " + this.getClass().getName());

    }
}
