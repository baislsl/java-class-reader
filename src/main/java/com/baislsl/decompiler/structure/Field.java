package com.baislsl.decompiler.structure;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.attribute.Attribute;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;

import java.io.DataInputStream;

public class Field extends FieldMethodBasic {
    private Field(int accessFlag, int nameIndex, int descriptorIndex, Attribute[] attributes) {
        super(accessFlag, nameIndex, descriptorIndex, attributes);
    }

    public static Field build(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        FieldMethodBasic basic = FieldMethodBasic.build(file, constantPools);
        return new Field(basic.getAccessFlag(), basic.getNameIndex(), basic.getDescriptorIndex(), basic.getAttributes());
    }

}
