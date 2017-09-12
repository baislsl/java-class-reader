package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;

public interface Descriptor {
    String[] description(Result result) throws DecompileException;
}
