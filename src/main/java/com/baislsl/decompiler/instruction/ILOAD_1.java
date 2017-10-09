package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class ILOAD_1 extends RefLoadInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.n = 1;
        return super.build(code, frame);
    }
}
