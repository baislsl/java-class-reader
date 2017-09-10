package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class SignatureAttr extends Attribute implements AttributeBuilder {
    private static final int SIGNATURE_IDNEX_SIZE = 2;
    private int signatureIndex;

    public SignatureAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        signatureIndex = Read.readBytes(file, SIGNATURE_IDNEX_SIZE);
        return this;
    }

    public int getSignatureIndex() {
        return signatureIndex;
    }
}
