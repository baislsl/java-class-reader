package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.constantPool.FieldrefTag;

public class PUTSTATIC extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        int index = get2u();
        Value value = opStack.pop();
        FieldrefTag fieldrefTag = (FieldrefTag) clazz.getConstantPool(index);
        result.append(fieldrefTag.getFullName(clazz))
                .append(" = ")
                .append(value)
                .append(";\n");
    }
}
