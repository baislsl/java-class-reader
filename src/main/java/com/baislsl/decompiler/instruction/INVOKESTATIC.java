package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.constantPool.ClassTag;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.MethodrefTag;
import com.baislsl.decompiler.structure.constantPool.NameAndTypeTag;
import com.baislsl.decompiler.utils.descriptor.FieldDescriptor;
import com.baislsl.decompiler.utils.descriptor.MethodDescriptor;

import java.util.ArrayList;
import java.util.List;

public class INVOKESTATIC extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        MethodrefTag methodCp = (MethodrefTag) clazz.getConstantPool(get2());
        NameAndTypeTag nameAndTypeIndexCp = (NameAndTypeTag) clazz.getConstantPool(methodCp.getNameAndTypeIndex());
        ClassTag classInfo = (ClassTag)clazz.getConstantPool(methodCp.getClassIndex());
        String methodClassName = clazz.getUTF8Info(classInfo.getNameIndex()).replaceAll("/", ".");
        String methodName = clazz.getUTF8Info(nameAndTypeIndexCp.getNameIndex());
        String descriptor = clazz.getUTF8Info(nameAndTypeIndexCp.getDescriptorIndex());
        int cnt = new MethodDescriptor(descriptor).getParamSize();

        List<Value> parameters = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            parameters.add(opStack.pop());
        }

        opStack.push(
            new Value(
                    methodClassName + "." + methodName
                            + "("
                            + parameters.stream().reduce((p1, p2) -> new Value(p1 + ", " + p2)).get()
                            + ");")
        );


    }
}
