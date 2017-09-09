package com.baislsl.decompiler.structure;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.attribute.Attribute;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;

import java.io.DataInputStream;

public class Method extends FieldMethodBasic {
    private Method(int accessFlag, int nameIndex, int descriptorIndex, Attribute[] attributes) {
        super(accessFlag, nameIndex, descriptorIndex, attributes);
    }

    public static Method build(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        FieldMethodBasic basic = FieldMethodBasic.build(file,constantPools);
        return new Method(basic.getAccessFlag(), basic.getNameIndex(), basic.getDescriptorIndex(), basic.getAttributes());
    }

}
