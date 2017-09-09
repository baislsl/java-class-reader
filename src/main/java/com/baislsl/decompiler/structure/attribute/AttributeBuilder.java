package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;

import java.io.DataInputStream;

public interface AttributeBuilder {
    Attribute build(DataInputStream file) throws DecompileException;
}
