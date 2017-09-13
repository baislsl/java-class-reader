package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public abstract class ElementValue implements Builder {
    private static final int TAG_SIZE = 1;
    static final int INDEX_SIZE = 2;
    private int tag;

    public static ElementValue getElementValue(DataInputStream file) throws DecompileException {
        int tag = Read.readBytes(file, TAG_SIZE);
        ElementValue value = getElementType(tag);
        value.build(file);
        return value;
    }

    ElementValue(int tag) {
        this.tag = tag;
    }


    private static ElementValue getElementType(int tag) throws DecompileException {
        switch (tag) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
            case 's':
                return new ConstantType(tag);
            case 'e':
                return new EnumType(tag);
            case 'c':
                return new ClassType(tag);
            case '@':
                return new AnnotationType(tag);
            case '[':
                return new ArrayType(tag);
            default:
                throw new DecompileException(
                        String.format("tag of value %d not match for element type", tag)
                );

        }
    }

    public int getTag() {
        return tag;
    }
}

class ConstantType extends ElementValue {
    private int constValueIndex;

    ConstantType(int tag) {
        super(tag);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        constValueIndex = Read.readBytes(file, INDEX_SIZE);
    }

    public int getConstValueIndex() {
        return constValueIndex;
    }
}

class EnumType extends ElementValue {
    private int typeNameIndex, constNameValue;

    EnumType(int tag) {
        super(tag);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        typeNameIndex = Read.readBytes(file, INDEX_SIZE);
        constNameValue = Read.readBytes(file, INDEX_SIZE);
    }

    public int getConstNameValue() {
        return constNameValue;
    }

    public int getTypeNameIndex() {
        return typeNameIndex;
    }
}


class ClassType extends ElementValue {
    private int classInfoIndex;

    ClassType(int tag) {
        super(tag);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        classInfoIndex = Read.readBytes(file, INDEX_SIZE);
    }

    public int getClassInfoIndex() {
        return classInfoIndex;
    }
}

class AnnotationType extends ElementValue {
    private Annotation annotation;
    AnnotationType(int tag) {
        super(tag);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        annotation = Annotation.getAnnotation(file);
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}

class ArrayType extends ElementValue {
    private final static int ELEMENT_VALUE_NUMBER_SIZE = 2;
    private ElementValue[] elementValues;

    ArrayType(int tag) {
        super(tag);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        int elementValuesNum = Read.readBytes(file, ELEMENT_VALUE_NUMBER_SIZE);
        elementValues = new ElementValue[elementValuesNum];
        for (int i = 0; i < elementValuesNum; i++) {
            elementValues[i] = ElementValue.getElementValue(file);
        }
    }

    public int getElementValuesSize() {
        return elementValues.length;
    }

    public ElementValue[] getElementValues() {
        return elementValues;
    }

    public ElementValue getElementValueAt(int index){
        return elementValues[index];
    }
}
