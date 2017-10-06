package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.LocalValue;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.utils.descriptor.FieldDescriptor;

/**
 * child class must overwrite the build method and definite the value of n
 * int the build method
 */
public abstract class RefStoreInstruction extends Instruction {
    protected int n;

    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value objectRef = opStack.pop();
        LocalValue value = localVariableTables.get(n, getStoreIndex());
        if(value.isAccess()){
            result.append(clazz.getUTF8Info(value.nameIndex()))
                    .append(" = ")
                    .append(objectRef)
                    .append(";\n");
        } else {
            value.setAccess(true);
            String name = clazz.getUTF8Info(value.nameIndex());
            String descriptor = clazz.getUTF8Info(value.descriptorIndex());
            result.append(FieldDescriptor.getFieldDescriptors(descriptor)[0]).append(" ")
                    .append(name)
                    .append(" = ")
                    .append(objectRef)
                    .append(";\n");

        }
    }
}
