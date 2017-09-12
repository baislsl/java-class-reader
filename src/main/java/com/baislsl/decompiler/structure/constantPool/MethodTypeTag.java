package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class MethodTypeTag extends ConstantPool {
    private static final int DESCRIPTOR_INDEX_SIZE = 2;
    private int descriptorIndex;

    public MethodTypeTag(int tag) {
        super(tag);
    }

    @Override
    public ConstantPool build(DataInputStream file) throws DecompileException {
        descriptorIndex = Read.readBytes(file, DESCRIPTOR_INDEX_SIZE);
        return this;
    }

    @Override
    public String[] description(Result result) throws DecompileException {
        return new String[]{
                "MethodType", result.getUTF8Info(descriptorIndex)
        };
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }
}
