package com.baislsl.decompiler.structure.constantPool;

public class DoubleTag extends LongDoubleBasic {
    public DoubleTag(int tag){
        super(tag);
    }

    public double getValue(){
        return (double)(((long) highByte << 32) | Integer.toUnsignedLong(lowByte));
    }
}
