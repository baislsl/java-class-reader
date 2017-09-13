package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class Annotation {
    private static final int INDEX_SIZE = 2;

    public int typeIndex;
    public ElementValuePairs[] elementValuePairs;

    private Annotation() {

    }

    public static Annotation getAnnotation(DataInputStream file) throws DecompileException {
        Annotation annotation = new Annotation();
        annotation.typeIndex = Read.readBytes(file, INDEX_SIZE);
        int elementValuePairsSize = Read.readBytes(file, INDEX_SIZE);
        annotation.elementValuePairs = new ElementValuePairs[elementValuePairsSize];
        for (int i = 0; i < elementValuePairsSize; i++) {
            annotation.elementValuePairs[i].elementIndex = Read.readBytes(file,ElementValuePairs.ELEMENT_INDEX_SIZE);
            annotation.elementValuePairs[i].elementValue = ElementValue.getElementValue(file);
        }
        return annotation;
    }

}

class ElementValuePairs {
    public final static int ELEMENT_INDEX_SIZE = 2;
    public int elementIndex;
    ElementValue elementValue;
}
