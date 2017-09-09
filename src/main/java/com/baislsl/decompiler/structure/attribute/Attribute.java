package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.Utf8Tag;
import com.baislsl.decompiler.utils.JVMUtf8;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public abstract class Attribute {
    private static final int ATTRIBUTE_NAME_INDEX_SIZE = 2;
    private static final int ATTRIBUTE_LENGTH_SIZE = 4;

    protected int attributeNameIndex;
    protected int attributeLength;

    public Attribute(int attributeNameIndex, int attributeLength) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
    }

    public static Attribute build(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        int attributeNameIndex = Read.readBytes(file, ATTRIBUTE_NAME_INDEX_SIZE);
        int attributeLength = Read.readBytes(file, ATTRIBUTE_LENGTH_SIZE);
        AttributeBuilder builder = getAttributeBuilder(attributeNameIndex, attributeLength, constantPools);
        return builder.build(file);
    }

    private static AttributeBuilder getAttributeBuilder(int attributeNameIndex,
                                                       int attributeLength,
                                                       ConstantPool[] constantPools)
            throws DecompileException {
        if (!(constantPools[attributeNameIndex] instanceof Utf8Tag)) {
            throw new DecompileException(
                    String.format("Can not find a UtfTag of attribute name index %d", attributeNameIndex)
            );
        } else {
            Utf8Tag utf8Tag = (Utf8Tag) constantPools[attributeNameIndex];
            String attributeName = JVMUtf8.decode(utf8Tag.getBytes());
            switch (attributeName) {
                case "ConstantValue":
                    return new ConstantValue(attributeNameIndex, attributeLength);


                default:
                    throw new DecompileException("Can not find attribute with name " + attributeName);
            }


        }
    }
}
