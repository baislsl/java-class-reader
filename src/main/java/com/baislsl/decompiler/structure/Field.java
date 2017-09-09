package com.baislsl.decompiler.structure;

public class Field  {
    private static final int ACCESS_FLAG_SIZE = 2;
    private static final int NAME_INDEX_SIZE = 2;
    private static final int DESCRIPTOR_INDEX_SIZE = 2;
    private static final int ATTRIBUTES_COUNT_SIZE = 2;

    private int accessFlag;
    private int nameIndex;
    private int descrtptorIndex;
    private int attributeCount;
    private Attribute[] attributes;

}
