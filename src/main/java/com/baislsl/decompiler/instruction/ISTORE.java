package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.LocalValue;
import com.baislsl.decompiler.engine.LocalVariableValueTable;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.utils.Javap;
import com.baislsl.decompiler.utils.descriptor.FieldDescriptor;

public class ISTORE extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();

        Value value = opStack.pop();
        LocalValue local = localVariableTables.get(get1(), getStoreIndex());
        if(local.isAccess()){
            result.append(clazz.getUTF8Info(local.nameIndex()))
                    .append(" = ")
                    .append(value)
                    .append(";\n");
        } else {
            local.setAccess(true);
            String name = clazz.getUTF8Info(local.nameIndex());
            String descriptor = clazz.getUTF8Info(local.descriptorIndex());
            result.append(FieldDescriptor.getFieldDescriptors(descriptor)[0]).append(" ")
                  .append(name)
                  .append(" = ")
                  .append(value)
                  .append(";\n");

        }


    }
}
