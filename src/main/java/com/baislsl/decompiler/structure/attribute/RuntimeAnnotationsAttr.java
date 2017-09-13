package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

/**
 * super class for RuntimeVisibleAnnotations and RuntimeInvisibleAnnotations
 */
public abstract class RuntimeAnnotationsAttr extends Attribute implements AttributeBuilder {
    private static int ANNOTATIONS_NUMBER_SIZE = 2;
    private Annotation[] annotations;

    public RuntimeAnnotationsAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int annotationsNumber = Read.readBytes(file, ANNOTATIONS_NUMBER_SIZE);
        annotations = new Annotation[annotationsNumber];
        for(int i = 0;i < annotationsNumber;i++){
            annotations[i] = Annotation.getAnnotation(file);
        }
        return this;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }
}
