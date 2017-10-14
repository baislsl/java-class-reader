package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.constantPool.ClassTag;
import com.baislsl.decompiler.structure.constantPool.MethodrefTag;
import com.baislsl.decompiler.structure.constantPool.NameAndTypeTag;
import com.baislsl.decompiler.utils.descriptor.Descriptor;
import com.baislsl.decompiler.utils.descriptor.MethodDescriptor;
import com.baislsl.decompiler.utils.descriptor.VoidDescriptor;

import java.util.ArrayList;
import java.util.List;

public class INVOKESPECIAL extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        MethodrefTag methodrefTag = (MethodrefTag) clazz.getConstantPool(get2u());
        NameAndTypeTag nameAndTypeIndexCp = (NameAndTypeTag) clazz.getConstantPool(methodrefTag.getNameAndTypeIndex());
        String descriptor = clazz.getUTF8Info(nameAndTypeIndexCp.getDescriptorIndex());
        MethodDescriptor methodDescriptor = new MethodDescriptor(descriptor);
        int cnt = methodDescriptor.getParamSize();
        Descriptor returnDescriptor = methodDescriptor.getReturnDescriptor();

        List<Value> parameters = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            parameters.add(opStack.pop());
        }
        Value objectRef = opStack.pop();
        StringBuilder ans = new StringBuilder();
        StringBuilder param = new StringBuilder();
        if (parameters.size() != 0)
            param.append(parameters.get(0));
        for (int i = 1; i < parameters.size(); i++) {
            param.append(", ").append(parameters.get(i));
        }

        String methodName = methodrefTag.getName(clazz);
        if (methodName.contains("<init>")) {
            // constructor
            if (isCallSuperClassMethod(objectRef, methodrefTag.getClassName(clazz))) {
                // call super constructor
                result.append("super(")
                        .append(param)
                        .append(");\n");
            } else {
                ans.append("new ")
                        .append(methodrefTag.getClassName(clazz))
                        .append("(")
                        .append(param)
                        .append(")");
                objectRef.setValue(ans.toString());
            }
        } else if (isCallSuperClassMethod(objectRef, methodrefTag.getClassName(clazz))) {
            // super method
            ans.append("super")
                    .append(".")
                    .append(methodrefTag.getName(clazz))
                    .append("(")
                    .append(param)
                    .append(")");
            if (returnDescriptor instanceof VoidDescriptor) {
                result.append(ans).append(";\n");
            } else {
                opStack.push(new Value(ans.toString(), false));
            }
        } else {
            // private method
            ans.append(objectRef)
                    .append(".")
                    .append(methodrefTag.getName(clazz))
                    .append("(")
                    .append(param)
                    .append(")");
            if (returnDescriptor instanceof VoidDescriptor) {
                result.append(ans).append(";\n");
            } else {
                opStack.push(new Value(ans.toString(), false));
            }
        }


    }

    private boolean isCallSuperClassMethod(Value objectRef, String className) throws DecompileException {
        if (!objectRef.toString().equals("this"))
            return false;
        ClassTag superClassTag = (ClassTag) clazz.getConstantPool(clazz.getSuperClass());
        String name = clazz.getUTF8Info(superClassTag.getNameIndex()).replaceAll("/", ".");
        return name.equals(className);
    }
}
