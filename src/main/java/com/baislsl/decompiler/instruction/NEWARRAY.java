package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class NEWARRAY extends Instruction {
    private static String[] arrayTypeTable = {
        null, null, null, null,
        "boolean", "char", "float", "double",
        "byte", "short", "int", "long"
    };
    @Override
    public void exec() throws DecompileException {
        super.exec();
        int atype = get1();
        Value count = opStack.pop();
        if(atype > 11 || atype < 4)
            throw new DecompileException("Error atype");
        opStack.push(
                new Value("new " + arrayTypeTable[atype] + "[" + count + "]")
        );

    }
}
