package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class ILOAD extends Instruction {

    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value value = localVariableTables.get(get1()).value();
        opStack.push(value);
    }
}
