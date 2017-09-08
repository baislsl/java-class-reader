package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.ConstantPool;

import java.io.DataInputStream;

public interface ConstantPoolBuilder {

    ConstantPool build(DataInputStream file) throws DecompileException;

}
