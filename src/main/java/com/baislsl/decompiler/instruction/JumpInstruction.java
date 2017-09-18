package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public abstract class JumpInstruction extends Instruction {
    public int getOffset() throws DecompileException {
        if (bytes.length == 2) {
            return get1();
        } else if (bytes.length == 3) {
            return get2();
        } else if (bytes.length == 5) {
            return
                    Byte.toUnsignedInt(bytes[4]) << 24
                            | Byte.toUnsignedInt(bytes[3]) << 16
                            | Byte.toUnsignedInt(bytes[2]) << 8
                            | Byte.toUnsignedInt(bytes[1]);
        } else {
            throw new DecompileException("Error jump offset length");
        }
    }
}
