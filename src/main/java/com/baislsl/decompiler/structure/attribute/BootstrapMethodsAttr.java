package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;
import com.sun.org.apache.regexp.internal.RE;

import java.io.DataInputStream;

public class BootstrapMethodsAttr extends Attribute implements AttributeBuilder {
    private final static int SIZE = 2;

    BootstrapMethods[] bootstrapMethods;

    public BootstrapMethodsAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int bootstrapMethodsSize = Read.readBytes(file, SIZE);
        bootstrapMethods = new BootstrapMethods[bootstrapMethodsSize];
        for (int i = 0; i < bootstrapMethodsSize; i++) {
            bootstrapMethods[i] = new BootstrapMethods();
            bootstrapMethods[i].bootstrapMethodRef = Read.readBytes(file, SIZE);
            int length = Read.readBytes(file, SIZE);
            bootstrapMethods[i].bootstrapArgument = new int[length];
            for (int j = 0; j < length; j++) {
                bootstrapMethods[i].bootstrapArgument[j] = Read.readBytes(file, SIZE);
            }
        }
        return this;
    }

    public BootstrapMethods[] getBootstrapMethods() {
        return bootstrapMethods;
    }
}

class BootstrapMethods {
    public int bootstrapMethodRef;
    public int[] bootstrapArgument;
}
