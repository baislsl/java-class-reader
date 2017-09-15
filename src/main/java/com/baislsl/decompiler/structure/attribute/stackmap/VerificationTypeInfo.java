package com.baislsl.decompiler.structure.attribute.stackmap;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.Name;
import com.baislsl.decompiler.structure.attribute.Builder;
import com.baislsl.decompiler.structure.constantPool.ClassTag;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public abstract class VerificationTypeInfo implements Builder, Name {
    private static final int TAG_SIZE = 1;
    int tag;


    VerificationTypeInfo() {

    }

    public static VerificationTypeInfo getVerificationTypeInfo(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        VerificationTypeInfo[] instance = {
                new TopVariableInfo(),
                new IntegerVariableInfo(),
                new FloatVariableInfo(),
                new DoubleVariableInfo(),
                new LongVariableInfo(),
                new NullVariableInfo(),
                new UninitializedThisVariableInfo(),
                new ObjectVariableInfo(constantPools),
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

    @Override
    public String name() throws DecompileException {
        return "top";
    }
}

class IntegerVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 1;
    }

    @Override
    public String name() throws DecompileException {
        return "int";
    }
}

class FloatVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 2;
    }

    @Override
    public String name() throws DecompileException {
        return "float";
    }
}

class DoubleVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 3;
    }

    @Override
    public String name() throws DecompileException {
        return "double";
    }
}

class LongVariableInfo extends VerificationTypeInfo {
    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 4;
    }

    @Override
    public String name() throws DecompileException {
        return "long";
    }
}

class NullVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 5;
    }

    @Override
    public String name() throws DecompileException {
        return "null";
    }
}

class UninitializedThisVariableInfo extends VerificationTypeInfo {

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 6;
    }

    @Override
    public String name() throws DecompileException {
        return "uninitializedThis";
    }
}

class ObjectVariableInfo extends VerificationTypeInfo {
    private static final int CPOOL_INDEX = 2;
    private int cpoolIndex;
    private ConstantPool[] constantPools;

    ObjectVariableInfo(ConstantPool[] constantPools){
        super();
        this.constantPools = constantPools;
    }

    @Override
    public void build(DataInputStream file)
            throws DecompileException {
        tag = 7;
        cpoolIndex = Read.readBytes(file, CPOOL_INDEX);
        this.constantPools = constantPools;
    }

    public static int getCpoolIndex() {
        return CPOOL_INDEX;
    }

    @Override
    public String name() throws DecompileException {
        int index = ((ClassTag)constantPools[cpoolIndex]).getNameIndex();
        return "Object\ntype=" + constantPools[index].name();
    }
}

class UninitializedVariableInfo extends VerificationTypeInfo {
    private static final int OFFSET_SIZE = 2;
    private int offset;

    @Override
    public void build(DataInputStream file) throws DecompileException {
        tag = 8;
        offset = Read.readBytes(file, OFFSET_SIZE);
    }

    @Override
    public String name() throws DecompileException {
        return "uninitialized\noffset=" + offset;
    }
}

