package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.structure.attribute.Code;

/**
 * ddiv, fdiv
 */
public abstract class DivideInstruction extends OperationInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        this.operator = "/";
        return super.build(code, frame);
    }
}
