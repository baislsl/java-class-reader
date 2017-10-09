package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.attribute.Code;

public class ILOAD extends RefLoadInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        Executable executable = super.build(code, frame);
        this.n = get1u();
        return executable;
    }
}
