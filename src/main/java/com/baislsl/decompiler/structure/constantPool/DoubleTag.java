package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;

public class DoubleTag extends LongDoubleBasic {
    public DoubleTag(int tag){
        super(tag);
    }

    @Override
    public String[] description(Result result) throws DecompileException {
        return new String[]{
          "Double", Double.toString(getValue())
        };
    }

    public double getValue(){
        return Double.longBitsToDouble(((long) highByte << 32) | Integer.toUnsignedLong(lowByte));
    }

    @Override
    public String toString() {
        return Double.toString(getValue());
    }
}
