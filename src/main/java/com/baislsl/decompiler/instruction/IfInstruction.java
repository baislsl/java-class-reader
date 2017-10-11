package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

/**
 * implement of if<cond>
 *     ifeq, ifne, iflt, ifge, ifgt, ifle
 */
public abstract class IfInstruction extends ConditionalJumpInstruction {
    protected String operator;

    @Override
    public void exec() throws DecompileException {
        super.exec();

        Value value = opStack.pop();

        // value operator 0
        int address = this.getFinalJumpObject();
        String action = "\n";
        if(address == frame.getTo()) action = "break;\n";
        if(address == frame.getFrom()) action = "continue;\n";

        result.append("if(")
                .append(value).append(operator).append("0")
                .append(") ")
                .append(action);
    }
}
