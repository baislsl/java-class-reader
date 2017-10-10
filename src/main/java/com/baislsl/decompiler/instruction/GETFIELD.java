package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.constantPool.FieldrefTag;

public class GETFIELD extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        int index = get2u();
        FieldrefTag field = (FieldrefTag) clazz.getConstantPool(index);
        String name =field.getName(clazz);
        Value objectRef = opStack.pop();
        opStack.push(
                new Value("(" + objectRef + ")." + name)
        );


    }
}
