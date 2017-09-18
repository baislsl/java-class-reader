package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * implement of if<cond>
 *     ifeq, ifne, iflt, ifge, ifgt, ifle
 */
public class IfInstruction extends ConditionalJumpInstruction {
    protected String operator;

    @Override
    public void exec() throws DecompileException {
        super.exec();

        Value value = opStack.pop();

        // value operator 0

    }
}
