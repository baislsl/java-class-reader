package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class MethodHandleTag extends ConstantPool implements ConstantPoolBuilder {
    private static final int REFERENCE_KIND_SIZE = 1;
    private static final int REFERENCE_INDEX_SIZE = 2;
    private int referenceKind;
    private int referenceIndex;

    public MethodHandleTag(int tag) {
        super(tag);
    }

    public ConstantPool build(DataInputStream file) throws DecompileException {
        referenceKind = Read.readBytes(file, REFERENCE_KIND_SIZE);
        referenceIndex = Read.readBytes(file, REFERENCE_INDEX_SIZE);
        return this;
    }

    public int getReferenceIndex() {
        return referenceIndex;
    }

    public int getReferenceKind() {
        return referenceKind;
    }
}
