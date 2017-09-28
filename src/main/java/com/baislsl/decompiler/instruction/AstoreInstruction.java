package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.engine.LocalValue;
import com.baislsl.decompiler.engine.Value;

/**
 * child class must overwrite the build method and definite the value of n
 * int the build method
 */
public abstract class AstoreInstruction extends Instruction {
    protected int n;

    @Override
    public void exec() throws DecompileException {
        super.exec();
        Value objectRef = opStack.pop();
        LocalValue value = localVariableTables.get(n, getStoreIndex());
        result.append(clazz.getUTF8Info(value.nameIndex()))
                .append("[")
                .append(n)
                .append("] = ")
                .append(objectRef)
                .append(";\n");



    }
}
