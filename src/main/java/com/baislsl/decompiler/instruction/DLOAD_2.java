package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class DLOAD_2 extends RefLoadInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.n = 2;
        return super.build(code, frame);
    }
}
