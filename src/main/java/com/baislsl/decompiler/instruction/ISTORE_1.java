package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class ISTORE_1 extends RefStoreInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.n = 1;
        return super.build(code, frame);
    }
}
