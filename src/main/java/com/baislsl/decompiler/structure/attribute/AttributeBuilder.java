package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;

import java.io.DataInputStream;

public interface AttributeBuilder {
    Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException;
}
