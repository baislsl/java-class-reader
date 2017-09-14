package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;

public interface Description {
    String[] description(Result result) throws DecompileException;
}
