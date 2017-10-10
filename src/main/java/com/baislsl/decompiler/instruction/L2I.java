package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

public class L2I extends ConvertTypeInstuction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.convertType = "int";
        return super.build(code, frame);
    }
}
