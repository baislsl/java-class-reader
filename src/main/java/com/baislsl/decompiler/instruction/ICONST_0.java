package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class ICONST_0 extends ConstInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.value = "0";
        return super.build(code, frame);
    }
}
