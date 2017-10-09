package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class ICONST_3 extends ConstInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.value = "3";
        return super.build(code, frame);
    }
}
