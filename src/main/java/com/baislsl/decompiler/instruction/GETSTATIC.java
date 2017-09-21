package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.constantPool.ClassTag;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.FieldrefTag;
import com.baislsl.decompiler.structure.constantPool.NameAndTypeTag;

public class GETSTATIC extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        ConstantPool cp = clazz.getConstantPool(get2());
        int nameTypeIndex = ((FieldrefTag) cp).getNameAndTypeIndex();
        int classIndex = ((FieldrefTag) cp).getClassIndex();

        NameAndTypeTag nameAndTypeTag = (NameAndTypeTag) clazz.getConstantPool(nameTypeIndex);
        ClassTag classTag = (ClassTag) clazz.getConstantPool(classIndex);

        String name = clazz.getUTF8Info(classTag.getNameIndex()).replaceAll("/", ".")
                + "."
                + clazz.getUTF8Info(nameAndTypeTag.getNameIndex()).replaceAll("/", ".");

        opStack.push(new Value(name));

    }
}
