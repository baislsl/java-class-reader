package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.Utf8Tag;
import com.baislsl.decompiler.utils.JVMUtf8;
import com.baislsl.decompiler.utils.Read;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.lang.reflect.Constructor;

public abstract class Attribute {
    private static final int ATTRIBUTE_NAME_INDEX_SIZE = 2;
    private static final int ATTRIBUTE_LENGTH_SIZE = 4;
    private static final Logger logger = LoggerFactory.getLogger(Attribute.class);

    protected int attributeNameIndex;
    protected int attributeLength;

    public Attribute(int attributeNameIndex, int attributeLength) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
    }

    public static Attribute getAttribute(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        int attributeNameIndex = Read.readBytes(file, ATTRIBUTE_NAME_INDEX_SIZE);
        int attributeLength = Read.readBytes(file, ATTRIBUTE_LENGTH_SIZE);
        AttributeBuilder builder = getAttributeBuilder(attributeNameIndex, attributeLength, constantPools);
        return builder.build(file, constantPools);
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

            // get the class name
            String className = Attribute.class.getName();

            // replace the last appear "Attribute" with @{String} attributeName
            className = className.substring(0, className.length() - "Attribute".length()) + attributeName + "Attr";
            try {
                Class<?> cl = Class.forName(className);
                Constructor<?> constructor = cl.getConstructor(int.class, int.class);
                return (AttributeBuilder)constructor.newInstance(attributeNameIndex, attributeLength);
            } catch (Exception e) {
                throw new DecompileException("Can not find attribute with name " + className);
            }
        }
    }
}
