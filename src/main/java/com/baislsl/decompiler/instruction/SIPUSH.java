package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class SIPUSH extends ConstInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        Executable executable = super.build(code, frame);
        this.value = Short.toString((short)get2());
        return executable;
    }
}
