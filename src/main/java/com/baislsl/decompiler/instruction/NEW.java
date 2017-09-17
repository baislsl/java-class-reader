package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class NEW extends Instruction {

    @Override
    public void exec() throws DecompileException {
        System.out.println("use instruction " + this.getClass().getName());

    }
}
