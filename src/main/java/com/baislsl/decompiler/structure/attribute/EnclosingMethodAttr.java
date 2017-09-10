package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class EnclosingMethodAttr extends Attribute implements AttributeBuilder {
    private final static int CLASS_INDEX_SIZE = 2;
    private final static int METHOD_INDEX_SIZE = 2;
    private int classIndex;
    private int methodIndex;

    public EnclosingMethodAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }


    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        classIndex = Read.readBytes(file, CLASS_INDEX_SIZE);
        methodIndex = Read.readBytes(file, METHOD_INDEX_SIZE);
        return this;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getMethodIndex() {
        return methodIndex;
    }
}
