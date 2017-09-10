package com.baislsl.decompiler.utils;

import com.baislsl.decompiler.DecompileException;

import java.io.DataInputStream;
import java.io.IOException;

public class Read {
    public static int readBytes(DataInputStream file, int size) throws DecompileException {
        byte[] buffer = new byte[size];
        readBytes(file, buffer);
        int result = 0;
        for (byte b : buffer) {
            result <<= 8;
            result |= 0xff & b;
        }
        return result;
    }

    public static void readBytes(DataInputStream file, byte[] bytes) throws DecompileException {
        readBytes(file, bytes, 0);
    }

    public static void readBytes(DataInputStream file, byte[] bytes, int off)
            throws DecompileException{
        try {
            if (file.read(bytes, off, bytes.length - off) != bytes.length - off)
                throw new DecompileException("file format error");
        } catch (IOException e) {
            throw new DecompileException("file format error");
        }
    }

}
