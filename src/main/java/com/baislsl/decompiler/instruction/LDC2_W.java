package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;

public class LDC2_W extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        ConstantPool cp = clazz.getConstantPool(get2());

        opStack.push(
                new Value(cp.name())
        );

    }
}
