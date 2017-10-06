package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class D2F extends ConvertTypeInstuction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.convertType = "float";
        return super.build(code, frame);
    }
}
