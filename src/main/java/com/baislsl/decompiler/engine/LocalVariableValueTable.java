package com.baislsl.decompiler.engine;

import com.baislsl.decompiler.Constants;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.attribute.LocalVariableTable;
import com.baislsl.decompiler.utils.descriptor.Descriptor;
import com.baislsl.decompiler.utils.descriptor.MethodDescriptor;

public class LocalVariableValueTable {

    private LocalValue[] values;


    public LocalVariableValueTable(LocalVariableTable[] localVariableTables, Result clazz, Method method)
            throws DecompileException {
        values = new LocalValue[localVariableTables.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = new LocalValue(localVariableTables[i], clazz);
        }
        String descriptorInfo = clazz.getUTF8Info(method.getDescriptorIndex());
        int descriptorsLength = new MethodDescriptor(descriptorInfo).getParamSize();

        int begin = 1;
        if (method.testAccessFlag(Constants.ACC_STATIC)) {
            begin = 0;
        }

        // set the value defined in the parameter access flag to true
        // this instruction avoid duplicate definitions of a parameter value
        for (int i = begin; i < begin + descriptorsLength; i++) {
            for(LocalValue value : values){
                if(value.index() == i)
                    value.setAccess(true);
                break;
            }
        }


    }

    public LocalValue get(int slot, int lineIndex) throws DecompileException {
        for (LocalValue value : values) {
            if (value.index() == slot
                    && value.startPC() <= lineIndex
                    && value.startPC() + value.length() > lineIndex) {
                return value;
            }
        }
        throw new DecompileException(
                String.format("Can not find value at slot %d with line index %d in local value table",
                        slot, lineIndex)
        );
    }

}

