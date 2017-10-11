package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.constantPool.*;

public abstract class LdcInstruction extends Instruction {
    protected int index;

    @Override
    public void exec() throws DecompileException {
        super.exec();
        ConstantPool cp = clazz.getConstantPool(index);
        String value;
        if (cp instanceof ClassTag) {
            int nameIndex = ((ClassTag) cp).getNameIndex();
            value = clazz.getUTF8Info(nameIndex);
        } else if (cp instanceof StringTag) {
            int nameIndex = ((StringTag) cp).getStringIndex();
            value = "\"" + clazz.getUTF8Info(nameIndex) + "\"";
        } else {
            value = cp.name();
        }

        opStack.push(
                new Value(value)
        );
    }
}
