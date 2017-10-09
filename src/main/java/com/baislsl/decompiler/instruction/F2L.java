package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class F2L extends ConvertTypeInstuction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.convertType = "long";
        return super.build(code, frame);
    }
}
