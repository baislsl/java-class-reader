package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;

public class IntegerTag extends FloatIntegerBasic {
    public IntegerTag(int tag) {
        super(tag);
    }

    @Override
    public String[] description(Result result) throws DecompileException {
        return new String[]{
                "Integer", Integer.toString(bytes)
        };
    }

    public int getValue() {
        return bytes;
    }

    @Override
    public String toString() {
        return Integer.toString(getValue());
    }

    @Override
    public String name() throws DecompileException {
        return toString();
    }
}
