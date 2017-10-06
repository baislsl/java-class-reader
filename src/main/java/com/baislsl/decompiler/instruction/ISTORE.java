package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.engine.LocalValue;
import com.baislsl.decompiler.engine.LocalVariableValueTable;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.attribute.Code;
import com.baislsl.decompiler.utils.Javap;
import com.baislsl.decompiler.utils.descriptor.FieldDescriptor;

public class ISTORE extends RefStoreInstruction {
    @Override
    public Executable build(Code code, Frame frame) {
        Executable executable  =super.build(code, frame);
        this.n = get1u();
        return executable;
    }
}
