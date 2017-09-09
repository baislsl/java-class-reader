package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class InvokeDynamicTag extends ConstantPool implements ConstantPoolBuilder {
    private static final int BOOTSTRAP_METHOD_ARRT_INDEX_SIZE = 2;
    private static final int NAME_AND_TYPE_INDEX_SIZE = 2;
    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    public InvokeDynamicTag(int tag){
        super(tag);
    }

    public ConstantPool build(DataInputStream file) throws DecompileException{
        bootstrapMethodAttrIndex = Read.readBytes(file, BOOTSTRAP_METHOD_ARRT_INDEX_SIZE);
        nameAndTypeIndex = Read.readBytes(file, NAME_AND_TYPE_INDEX_SIZE);
        return this;
    }

    public int getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
