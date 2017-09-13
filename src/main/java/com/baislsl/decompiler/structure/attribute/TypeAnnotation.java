package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class TypeAnnotation {
    private final static int TARGER_TYPE_SIZE = 1;
    private final static int TYPE_INDEX_SIZE = 2;
    private final static int ELEMENT_VALUE_PAIR_NUMBER_SIZE = 2;
    private int targetType;
    private TargetInfo targetInfo;
    private TypePath typePath;
    private int typeIndex;
    private ElementValuePairs[] elementValuePairs;

    private TypeAnnotation() {

    }

    public static TypeAnnotation getTypeAnnotation(DataInputStream file) throws DecompileException {
        TypeAnnotation typeAnnotation = new TypeAnnotation();
        typeAnnotation.targetType = Read.readBytes(file, TARGER_TYPE_SIZE);
        typeAnnotation.targetInfo = TargetInfo.getTargetInfo(file, typeAnnotation.targetType);
        typeAnnotation.typePath = new TypePath();
        int typePathNumber = Read.readBytes(file, TypePath.PATH_NUMBER_SIZE);
        typeAnnotation.typePath.paths = new TypePath.Path[typePathNumber];
        for(int i = 0; i<typePathNumber;i++){
            typeAnnotation.typePath.paths[i].typePathKind = Read.readBytes(file, TypePath.TYPE_SIZE);
            typeAnnotation.typePath.paths[i].typeArgumentIndex = Read.readBytes(file, TypePath.TYPE_SIZE);
        }
        typeAnnotation.typeIndex = Read.readBytes(file, TYPE_INDEX_SIZE);
        int elementValuePairsNum = Read.readBytes(file, ELEMENT_VALUE_PAIR_NUMBER_SIZE);
        typeAnnotation.elementValuePairs = new ElementValuePairs[elementValuePairsNum];
        for (int i = 0; i < elementValuePairsNum; i++) {
            typeAnnotation.elementValuePairs[i].elementIndex = Read.readBytes(file, ElementValuePairs.ELEMENT_INDEX_SIZE);
            typeAnnotation.elementValuePairs[i].elementValue = ElementValue.getElementValue(file);
        }
        return typeAnnotation;
    }

    public int getTargetType() {
        return targetType;
    }

    public TargetInfo getTargetInfo() {
        return targetInfo;
    }

    public TypePath getTypePath() {
        return typePath;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public ElementValuePairs[] getElementValuePairs() {
        return elementValuePairs;
    }
}

class TypePath {
    public class Path {
        int typePathKind;
        int typeArgumentIndex;
    }

    public static final int PATH_NUMBER_SIZE = 1;
    public static final int TYPE_SIZE = 1;
    Path[] paths;

}