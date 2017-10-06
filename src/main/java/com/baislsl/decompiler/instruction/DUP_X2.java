package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class DUP_X2 extends Instruction {
    // TODO: distinguish category 1 and 2 computational type

    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value value1 = opStack.pop();
        Value value2 = opStack.pop();
        opStack.push(value1);
        opStack.push(value2);
        opStack.push(value1);
    }
}
