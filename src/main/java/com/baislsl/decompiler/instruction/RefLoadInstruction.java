package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * child class must overwrite the build method and definite the value of n
 * int the build method
 */
public abstract class RefLoadInstruction extends Instruction {
    protected int n;

    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(
                // 压入一个新的Value，保证local variable table只读
                new Value(localVariableTables.get(n, getStoreIndex()).value().toString())
        );
    }
}
