package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;

import java.io.DataInputStream;

public class DeprecatedAttr extends Attribute implements AttributeBuilder {
    public DeprecatedAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        if (attributeLength != 0)
            throw new DecompileException("Attribute length of synthetic attribute must be 0");
        return this;
    }
}
