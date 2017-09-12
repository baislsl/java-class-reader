package com.baislsl.decompiler.structure.attribute.stackmap;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public abstract class VerificationTypeInfo implements Builder {
    private static final int TAG_SIZE = 1;
    int tag;


    VerificationTypeInfo() {

    }

    public static VerificationTypeInfo getVerificationTypeInfo(DataInputStream file) throws DecompileException {
        VerificationTypeInfo[] instance = {
                new TopVariableInfo(),
                new IntegerVariableInfo(),
                new FloatVariableInfo(),
                new DoubleVariableInfo(),
                new LongVariableInfo(),
                new NullVariableInfo(),
                new UninitializedThisVariableInfo(),
                new ObjectVariableInfo(),
                new UninitializedVariableInfo()
        };
        int tag = Read.readBytes(file, TAG_SIZE);
        if (tag >= 8)
            throw new DecompileException(String.format("Tag size of %d can not match in verification type info", tag));
        instance[tag].build(file);
        return instance[tag];
    }

}

class TopVariableInfo extends VerificationTypeInfo {
    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 0;
    }
}

class IntegerVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 1;
    }
}

class FloatVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 2;
    }
}

class DoubleVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 3;
    }
}

class LongVariableInfo extends VerificationTypeInfo {
    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 4;
    }
}

class NullVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 5;
    }
}

class UninitializedThisVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 6;
    }
}

class ObjectVariableInfo extends VerificationTypeInfo {
    private static final int CPOOL_INDEX = 2;
    int cpoolIndex;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 7;
        cpoolIndex = Read.readBytes(file, CPOOL_INDEX);
    }
}

class UninitializedVariableInfo extends VerificationTypeInfo {
    private static final int OFFSET_SIZE = 2;
    int offset;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 8;
        offset = Read.readBytes(file, OFFSET_SIZE);
    }
}

