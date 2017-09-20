package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.Value;

public class LDC extends Instruction {
    /**
     *  UNFINISHed yet !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1111
     *
     *
     *
     * @throws DecompileException
     */



    @Override
    public void exec() throws DecompileException {
        super.exec();
        opStack.push(
                new Value(clazz.getConstantPool(get1u()).name())
        );
    }
}
