package com.baislsl.decompiler.structure;

import com.baislsl.decompiler.DecompileException;

public interface Name {
    default String name() throws DecompileException {
        return null;
    }

}
