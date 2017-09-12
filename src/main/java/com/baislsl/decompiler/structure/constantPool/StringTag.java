package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class StringTag extends ConstantPool {
    private static final int STRING_INDEX_SIZE = 2;
    private int stringIndex;

    public StringTag(int tag) {
        super(tag);
    }

    @Override
    public ConstantPool build(DataInputStream file) throws DecompileException {
        stringIndex = Read.readBytes(file, STRING_INDEX_SIZE);
        return this;
    }

    @Override
    public String[] description(Result result) throws DecompileException {
        return new String[]{
                "String", "#" + Integer.toString(stringIndex), result.getUTF8Info(stringIndex)
        };
    }


    public int getStringIndex() {
        return stringIndex;
    }
}
