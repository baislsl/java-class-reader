package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class MethodTypeTag extends ConstantPool implements ConstantPoolBuilder {
    private static final int DESCRIPTOR_INDEX_SIZE = 2;
    private int descriptorIndex;

    public MethodTypeTag(int tag) {
        super(tag);
    }

    public ConstantPool build(DataInputStream file) throws DecompileException {
        descriptorIndex = Read.readBytes(file, DESCRIPTOR_INDEX_SIZE);
        return this;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }
}
