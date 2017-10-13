package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.constantPool.ClassTag;
import com.baislsl.decompiler.structure.constantPool.FMIBasic;

public class ANEWARRAY extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value count = opStack.pop();
        int index = get2();
        ClassTag classTag = (ClassTag)clazz.getConstantPool(index);
        String name = clazz.getUTF8Info(classTag.getNameIndex()).replaceAll("/", ".");

        opStack.push(
                new Value("new " + name + "[" + count + "]")
        );
    }
}
