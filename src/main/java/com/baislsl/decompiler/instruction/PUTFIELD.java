package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.constantPool.FieldrefTag;

public class PUTFIELD extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value value = opStack.pop();
        Value objectRef = opStack.pop();

        int index = get2u();
        FieldrefTag fieldrefTag  = (FieldrefTag) clazz.getConstantPool(index);

        result.append(objectRef)
                .append(".")
                .append(fieldrefTag.getName(clazz))
                .append(" = ")
                .append(value)
                .append("\n");

    }
}
