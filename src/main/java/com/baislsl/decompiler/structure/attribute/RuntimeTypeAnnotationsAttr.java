package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

/**
 * super class for RuntimeInvisibleParameterAnnotations and RuntimeVisibleTypeAnnotations
 */
public abstract class RuntimeTypeAnnotationsAttr extends Attribute implements AttributeBuilder {
    private static final int TYPE_ANNOTATIONS_NUMBER_SIZE = 2;
    private TypeAnnotation[] typeAnnotations;

    public RuntimeTypeAnnotationsAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int annotationsNum = Read.readBytes(file, TYPE_ANNOTATIONS_NUMBER_SIZE);
        typeAnnotations = new TypeAnnotation[annotationsNum];
        for (int i = 0; i < annotationsNum; i++) {
            typeAnnotations[i] = TypeAnnotation.getTypeAnnotation(file);
        }
        return this;
    }

    public TypeAnnotation[] getTypeAnnotations() {
        return typeAnnotations;
    }
}
