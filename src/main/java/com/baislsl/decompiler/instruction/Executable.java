package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public interface Executable {

    Executable build(Code code, Frame frame);

    void exec() throws DecompileException;

    int getCountIndex();

    int getStoreIndex();

}
