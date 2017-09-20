package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.attribute.Code;

public class ICONST_N extends Instruction {
    protected int n = 0;
    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(new Value(n));
    }
}
