package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Type;

public class ISTORE extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();

        Type value = opStack.pop();
        int index = localVariableTables[(int)bytes[0]].nameIndex;

        result.append(clazz.getUTF8Info(index))
              .append(" = ")
              .append(value)
              .append("\n");

    }
}
