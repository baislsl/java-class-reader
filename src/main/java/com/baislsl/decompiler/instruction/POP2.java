package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public class POP2 extends Instruction {
    /**
     * TODO:
     * Form 1:
     *  ..., value2, value1 →
     * ...
     * where each of value1 and value2 is a value of a category 1
     * computational type (§2.11.1).
     * Form 2:
     * ..., value →
     * ...
     * where value is a value of a category 2 computational type
     */
    @Override
    public void exec() throws DecompileException {
        opStack.pop();
        opStack.pop();
        super.exec();
    }
}
