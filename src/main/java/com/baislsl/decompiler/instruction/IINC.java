package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class IINC extends Instruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        result.append(localVariableTables.get(Byte.toUnsignedInt(bytes[1]), getStoreIndex()).value())
              .append(" += ")
              .append((int)bytes[2])
              .append("\n");
    }
}
