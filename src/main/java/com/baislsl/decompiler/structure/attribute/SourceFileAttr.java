package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class SourceFileAttr extends Attribute implements AttributeBuilder {
    private static final int SOURCEFILE_INDEX_SIZE = 2;
    private int sourcefileIndex;

    public SourceFileAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        sourcefileIndex = Read.readBytes(file, SOURCEFILE_INDEX_SIZE);
        return this;
    }

    public int getSourcefileIndex() {
        return sourcefileIndex;
    }

    @Override
    public String name() throws DecompileException {
        String sourceFileName = constantPools[sourcefileIndex].name();
        return "SourceFile: \"" + sourceFileName +  "\"\n";
    }
}
