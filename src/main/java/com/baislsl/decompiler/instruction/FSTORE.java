package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class FSTORE extends RefStoreInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        Executable executable = super.build(code, frame);
        this.n = get1u();
        return executable;
    }
}
