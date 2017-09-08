package com.baislsl.decompiler.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;
import java.io.IOException;

public class Utf8Tag extends ConstantPool implements ConstantPoolBuilder {
    private static final int LENGTH_SIZE = 2;
    private int length;
    private byte[] bytes;

    public Utf8Tag(int tag) {
        super(tag);
    }

    @Override
    public ConstantPool build(DataInputStream file) throws DecompileException {
        length = Read.readBytes(file, LENGTH_SIZE);
        bytes = new byte[length];
        Read.readBytes(file, bytes);
        return this;
    }

    public int getLength() {
        return length;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
