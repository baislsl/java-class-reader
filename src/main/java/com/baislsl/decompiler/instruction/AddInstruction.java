package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.attribute.Code;

/**
 * dadd
 */
public abstract class AddInstruction extends OperationInstuction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.operator = "+";
        return super.build(code, frame);
    }
}
