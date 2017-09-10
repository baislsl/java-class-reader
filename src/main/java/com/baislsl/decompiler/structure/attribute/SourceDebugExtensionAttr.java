package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class SourceDebugExtensionAttr extends Attribute implements AttributeBuilder {
    private static final int EXTENSION_SIZE = 1;
    private int debugExtension[];

    public SourceDebugExtensionAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
        debugExtension = new int[attributeLength];
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        for (int i = 0; i < attributeLength; i++) {
            debugExtension[i] = Read.readBytes(file, EXTENSION_SIZE);
        }
        return this;
    }

    public int[] getDebugExtension() {
        return debugExtension;
    }
}
