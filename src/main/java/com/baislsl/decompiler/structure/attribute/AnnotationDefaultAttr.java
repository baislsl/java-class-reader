package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;

import java.io.DataInputStream;

public class AnnotationDefaultAttr extends Attribute implements AttributeBuilder {

    private ElementValue defaultValue;

    public AnnotationDefaultAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        defaultValue = ElementValue.getElementValue(file);
        return this;
    }

    public ElementValue getDefaultValue() {
        return defaultValue;
    }
}
