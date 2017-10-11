package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class LDC extends LdcInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        Executable executable = super.build(code, frame);
        this.index = get1u();
        return executable;
    }
}
