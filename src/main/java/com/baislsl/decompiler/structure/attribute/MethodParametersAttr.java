package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class MethodParametersAttr extends Attribute implements AttributeBuilder {
    private static final int PARAMETER_COUNT_SIZE = 1;
    private static final int PARAMETER_NAEM_INDEX_SIZE = 2;
    private static final int PARAMETER_ACCESS_FLAG_SIZE = 2;

    private Parameter[] parameters;

    public MethodParametersAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int parameterCount = Read.readBytes(file, PARAMETER_COUNT_SIZE);
        parameters = new Parameter[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            parameters[i] = new Parameter();
            parameters[i].nameIndex = Read.readBytes(file, PARAMETER_NAEM_INDEX_SIZE);
            parameters[i].accessFlag = Read.readBytes(file, PARAMETER_ACCESS_FLAG_SIZE);
        }
        return this;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

}
