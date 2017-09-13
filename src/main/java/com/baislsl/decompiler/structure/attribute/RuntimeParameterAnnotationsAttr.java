package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

/**
 * super class for RuntimeInvisibleParameterAnnotationsAttr and RuntimeVisibleParameterAnnotationsAttr
 */
public abstract class RuntimeParameterAnnotationsAttr extends Attribute
        implements AttributeBuilder {
    private static final int NUM_PARAMETERS_SIZE = 1;
    private ParameterAnnotation[] parameterAnnotations;

    public RuntimeParameterAnnotationsAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int parameterAnnotationsSize = Read.readBytes(file, NUM_PARAMETERS_SIZE);
        parameterAnnotations = new ParameterAnnotation[parameterAnnotationsSize];
        for (int i = 0; i < parameterAnnotationsSize; i++) {
            int annotationSize = Read.readBytes(file, ParameterAnnotation.NUM_ANNOTATIONS_SIZE);
            parameterAnnotations[i].annotation = new Annotation[annotationSize];
            for (int j = 0; j < annotationSize; j++) {
                parameterAnnotations[i].annotation[j] = Annotation.getAnnotation(file);
            }
        }
        return this;
    }
}

class ParameterAnnotation {
    public static final int NUM_ANNOTATIONS_SIZE = 2;
    public Annotation[] annotation;

}