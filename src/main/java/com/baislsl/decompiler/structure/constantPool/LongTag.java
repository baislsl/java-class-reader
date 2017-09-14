package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;

public class LongTag extends LongDoubleBasic {
    public LongTag(int tag) {
        super(tag);
    }

    @Override
    public String[] description(Result result) throws DecompileException {
        return new String[]{
                "Long", Long.toString(getValue())
        };
    }

    public long getValue() {
        return ((long) highByte << 32) | Integer.toUnsignedLong(lowByte);
    }

    @Override
    public String toString() {
        return Long.toString(getValue());
    }
}
