package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

/**
 * this class implements
 *      LongTag
 *      DoubleTag
 */
public abstract class LongDoubleBasic extends ConstantPool implements ConstantPoolBuilder {
    private static final int HIGH_BYTE_SIZE = 4;
    private static final int LOW_BYTE_SIZE = 4;
    protected int highByte, lowByte;

    LongDoubleBasic(int tag){
        super(tag);
    }

    @Override
    public ConstantPool build(DataInputStream file) throws DecompileException{
        highByte = Read.readBytes(file, HIGH_BYTE_SIZE);
        lowByte = Read.readBytes(file, LOW_BYTE_SIZE);
        return this;
    }

    public int getHighByte() {
        return highByte;
    }

    public int getLowByte() {
        return lowByte;
    }


}
