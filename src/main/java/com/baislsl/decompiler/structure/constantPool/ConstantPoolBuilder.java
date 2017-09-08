package com.baislsl.decompiler.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.ConstantPool;

import java.io.DataInputStream;

public interface ConstantPoolBuilder {

    ConstantPool build(DataInputStream file) throws DecompileException;

}
