package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class NameAndTypeTag extends ConstantPool  {
    private static final int NAME_INDEX_SIZE = 2;
    private static final int DESCRIPTOR_INDEX_SIZE = 2;

    private int nameIndex;
    private int descriptorIndex;

    public NameAndTypeTag(int tag) {
        super(tag);
    }

    @Override
    public ConstantPool build(DataInputStream file) throws DecompileException {
        nameIndex = Read.readBytes(file, NAME_INDEX_SIZE);
        descriptorIndex = Read.readBytes(file, DESCRIPTOR_INDEX_SIZE);
        return this;
    }

    @Override
    public String[] description(Result result) throws DecompileException {
        return new String[]{
                "NameAndType", "#" + Integer.toString(nameIndex) + ".#" + Integer.toString(descriptorIndex),
                result.getUTF8Info(nameIndex), result.getUTF8Info(descriptorIndex)
        };
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }
}
