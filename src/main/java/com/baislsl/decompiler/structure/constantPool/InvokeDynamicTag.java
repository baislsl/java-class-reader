package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class InvokeDynamicTag extends ConstantPool {
    private static final int BOOTSTRAP_METHOD_ARRT_INDEX_SIZE = 2;
    private static final int NAME_AND_TYPE_INDEX_SIZE = 2;
    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    public InvokeDynamicTag(int tag) {
        super(tag);
    }

    @Override
    public ConstantPool build(DataInputStream file) throws DecompileException {
        bootstrapMethodAttrIndex = Read.readBytes(file, BOOTSTRAP_METHOD_ARRT_INDEX_SIZE);
        nameAndTypeIndex = Read.readBytes(file, NAME_AND_TYPE_INDEX_SIZE);
        return this;
    }

    @Override
    public String[] description(Result result) throws DecompileException {
        Method method = result.getMethod(bootstrapMethodAttrIndex);
        String methodName = result.getUTF8Info(method.getNameIndex());
        String[] nameAndType = result.getConstantPool(nameAndTypeIndex).description(result);
        return new String[]{
                "InvokeDynamic", "#" + Integer.toString(bootstrapMethodAttrIndex) + ".#" + Integer.toString(nameAndTypeIndex),
                methodName, nameAndType[3], nameAndType[4]
        };
    }

    public int getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
