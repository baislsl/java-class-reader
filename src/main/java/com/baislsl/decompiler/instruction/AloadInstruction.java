package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

/**
 * child class must overwrite the build method and definite the value of n
 * int the build method
 */
public abstract class AloadInstruction extends Instruction {
    protected int n;

    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(
                localVariableTables.get(n, getStoreIndex()).value()
        );
    }
}
