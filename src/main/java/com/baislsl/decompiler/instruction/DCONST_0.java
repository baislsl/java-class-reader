package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class DCONST_0 extends DoubleConstInstuction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.value = "0.0";
        return super.build(code, frame);
    }
}
