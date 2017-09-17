package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.engine.Frame;
import com.baislsl.decompiler.engine.LocalVariableValueTable;
import com.baislsl.decompiler.engine.Value;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.attribute.Code;
import com.baislsl.decompiler.structure.attribute.CodeAttr;
import com.baislsl.decompiler.structure.attribute.Parameter;

import java.util.Stack;

public abstract class Instruction implements Executable{

    protected byte[] bytes;
    protected Code code;
    protected Method method;
    protected Result clazz;

    protected StringBuilder result;
    protected Stack<Value> opStack;
    protected CodeAttr codeAttr;

    protected LocalVariableValueTable localVariableTables;
    protected int[] exceptionIndexes;
    protected Parameter[] parameters;

    public Instruction(){}

    @Override
    public Executable build(Code code, Frame frame) {
        this.bytes = code.getBytes();
        this.code = code;
        this.method = frame.getMethod();
        this.clazz = frame.getClazz();

        this.result = frame.getResult();
        this.opStack = frame.getOpStack();
        this.codeAttr = frame.getCodeAttr();
        this.localVariableTables = frame.getLocalVariableTables();
        this.exceptionIndexes = frame.getExceptionIndexes();
        this.parameters = frame.getParameters();

        return this;
    }

    @Override
    public void exec() throws DecompileException {

    }

    protected int get1(){
        return Byte.toUnsignedInt(bytes[1]);
    }

    protected int get2(){
        return ((int)bytes[1]) << 2 | Byte.toUnsignedInt(bytes[2]);
    }
}
