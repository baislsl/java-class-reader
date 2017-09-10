package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class ExceptionsAttr extends Attribute implements AttributeBuilder {
    private static final int EXCEPTION_NUMBER_SIZE = 2;
    private static final int EXCEPTION_INDEX = 2;

    private int[] exceptionIndexTable;

    public ExceptionsAttr(int attributeNameIndex, int attributeLength){
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int exceptionNumber = Read.readBytes(file, EXCEPTION_NUMBER_SIZE);
        exceptionIndexTable = new int[exceptionNumber];
        for(int i = 0;i<exceptionNumber;i++){
            exceptionIndexTable[i] = Read.readBytes(file, EXCEPTION_INDEX);
        }
        return this;
    }

    public int[] getExceptionIndexTable() {
        return exceptionIndexTable;
    }
}
