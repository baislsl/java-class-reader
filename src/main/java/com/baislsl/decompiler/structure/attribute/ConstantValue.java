package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class ConstantValue extends Attribute implements AttributeBuilder {
    private static final int CONSTANT_VALUE_INDEX_SIZE = 2;

    private int constantValueIndex;

    public ConstantValue(int attributeNameIndex,int attributeLength) throws DecompileException{
        super(attributeNameIndex, attributeLength);
        if(attributeLength != 2)
            throw new DecompileException("the attribute length of ConstantValue_attribute must be 2");
    }

    @Override
    public Attribute build(DataInputStream file) throws DecompileException {
        constantValueIndex = Read.readBytes(file, CONSTANT_VALUE_INDEX_SIZE);
        return this;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}
