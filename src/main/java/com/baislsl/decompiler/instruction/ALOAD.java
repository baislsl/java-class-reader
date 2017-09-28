package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class ALOAD extends AloadInstruction {

    @Override
    public Executable build(Code code, Frame frame) {
        this.n = get1u();
        return super.build(code, frame);
    }

}
