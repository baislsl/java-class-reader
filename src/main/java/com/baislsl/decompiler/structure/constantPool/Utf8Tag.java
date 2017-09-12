package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.utils.JVMUtf8;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class Utf8Tag extends ConstantPool {
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

    @Override
    public String[] description(Result result) throws DecompileException {
        return new String[]{
                "Utf8", getString()
        };
    }

    public String getString() throws DecompileException {
        return JVMUtf8.decode(bytes);
    }

    public int getLength() {
        return length;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
