package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class ClassTag extends ConstantPool implements ConstantPoolBuilder {
    private static final int NAME_INDEX_SIZE = 2;
    private int nameIndex;

    public ClassTag(int tag) {
        super(tag);
    }

    @Override
    public ConstantPool build(DataInputStream file) throws DecompileException {
        nameIndex = Read.readBytes(file, NAME_INDEX_SIZE);
        return this;
    }

    public int getNameIndex() {
        return nameIndex;
    }
}
