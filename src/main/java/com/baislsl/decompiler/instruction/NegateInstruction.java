package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * dneg, fneg, ineg
 */
public abstract class NegateInstruction extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(
                new Value("-(" + opStack.pop() + ")")
        );
    }
}
