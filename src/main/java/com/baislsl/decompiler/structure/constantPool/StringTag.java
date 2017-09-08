package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class StringTag extends ConstantPool implements ConstantPoolBuilder {
    public static final int STRING_INDEX_SIZE = 2;
    private int stringIndex;

    public StringTag(int tag) {
        super(tag);
    }

    @Override
    public ConstantPool build(DataInputStream file) throws DecompileException {
        stringIndex = Read.readBytes(file, STRING_INDEX_SIZE);
        return this;
    }

    public int getStringIndex() {
        return stringIndex;
    }
}
