package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class DCMPG extends DoubleCompareInstuction {
    // TODO: how to decompile sign(a - b)
    @Override
    public Executable build(Code code, Frame frame) {
        this.operator = ">";
        return super.build(code, frame);
    }
}
