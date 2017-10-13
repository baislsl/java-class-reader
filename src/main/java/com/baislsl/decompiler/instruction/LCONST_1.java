package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class LCONST_1 extends ConstInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.value = "1L";
        return super.build(code, frame);
    }
}
