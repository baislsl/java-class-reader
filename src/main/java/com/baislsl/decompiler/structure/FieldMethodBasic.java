package com.baislsl.decompiler.structure;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.attribute.Attribute;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class FieldMethodBasic {
    protected static final int ACCESS_FLAG_SIZE = 2;
    protected static final int NAME_INDEX_SIZE = 2;
    protected static final int DESCRIPTOR_INDEX_SIZE = 2;
    protected static final int ATTRIBUTES_COUNT_SIZE = 2;

    private int accessFlag;
    private int nameIndex;
    private int descriptorIndex;
    private Attribute[] attributes;

    protected FieldMethodBasic(int accessFlag, int nameIndex, int descriptorIndex, Attribute[] attributes) {
        this.accessFlag = accessFlag;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    public static FieldMethodBasic build(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        int accessFlag = Read.readBytes(file, ACCESS_FLAG_SIZE);
        int nameIndex = Read.readBytes(file, NAME_INDEX_SIZE);
        int descriptorIndex = Read.readBytes(file, DESCRIPTOR_INDEX_SIZE);
        int attributeCount = Read.readBytes(file, ATTRIBUTES_COUNT_SIZE);
        Attribute[] attributes = new Attribute[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            attributes[i] = Attribute.getAttribute(file, constantPools);
        }
        return new FieldMethodBasic(accessFlag, nameIndex, descriptorIndex, attributes);
    }


    public int getAccessFlag() {
        return accessFlag;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public int getAttributeCount() {
        return attributes.length;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }
}
