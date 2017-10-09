package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class FCONST_1 extends ConstInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.value = "1.0f";
        return super.build(code, frame);
    }
}
