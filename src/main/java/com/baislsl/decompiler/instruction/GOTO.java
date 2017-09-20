package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class GOTO extends UnconditionalJumpInstruction {
    @Override
    public void exec() throws DecompileException {
        super.exec();
        if(getJumpObject() == frame.getTo())
            result.append("break;");
        else if(getJumpObject() == frame.getFrom()){
            result.append("continue;");
        } else {
            // throw new DecompileException("No support now ?");
            System.out.println("No support now ?");
        }
    }
}
