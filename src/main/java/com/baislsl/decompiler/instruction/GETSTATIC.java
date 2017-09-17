package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.FieldrefTag;
import com.baislsl.decompiler.structure.constantPool.NameAndTypeTag;

public class GETSTATIC extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        ConstantPool cp = clazz.getConstantPool(get2());
        int nameTypeIndex = ((FieldrefTag) cp).getNameAndTypeIndex();
        ConstantPool cp2 = clazz.getConstantPool(nameTypeIndex);
        String name = "this." + clazz.getUTF8Info(((NameAndTypeTag)cp2).getNameIndex());

        opStack.push(new Value(name));

    }
}
