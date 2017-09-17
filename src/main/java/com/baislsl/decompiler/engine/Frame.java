package com.baislsl.decompiler.engine;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.instruction.Instruction;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.attribute.*;

import java.lang.reflect.Constructor;
import java.util.Stack;


public class Frame {
    private Result clazz;
    private Method method;
    private StringBuilder result;
    private Stack<Type> opStack;
    private CodeAttr codeAttr;
    private Code[] codes;
    private LocalVariableTable[] localVariableTables;
    private int[] exceptionIndexes;
    private Parameter[] parameters;

    public Frame(Result clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
        this.opStack = new Stack<>();
        this.result = new StringBuilder();

        for (Attribute attribute : method.getAttributes()) {
            if (attribute instanceof CodeAttr) {
                this.codeAttr = (CodeAttr) attribute;
                this.codes = codeAttr.getCodes();
            } else if (attribute instanceof ExceptionsAttr) {
                exceptionIndexes = ((ExceptionsAttr) attribute).getExceptionIndexTable();
            } else if (attribute instanceof MethodParametersAttr) {
                parameters = ((MethodParametersAttr) attribute).getParameters();
            }
        }
        if (codeAttr != null) {
            for (Attribute attribute : codeAttr.getAttributes()) {
                if (attribute instanceof LocalVariableTableAttr) {
                    localVariableTables = ((LocalVariableTableAttr) attribute).getTables();
                }
            }
        }
    }

    public Frame exec() throws DecompileException {
        for (Code code : codes) {
            accept(code);
        }
        return this;
    }

    private void accept(Code code) throws DecompileException {
        String instructionClassPath = Instruction.class.getName();
        try {
            Class cl = Class.forName(
                    instructionClassPath.substring(0, instructionClassPath.length() - "Instruction".length())
                            + code.getName().toUpperCase()
            );
            Constructor constructor = cl.getConstructor();
            Instruction instruction = (Instruction) constructor.newInstance();
            instruction.build(code, this).exec();
        } catch (ReflectiveOperationException e) {
            // throw new DecompileException(e);
            System.out.println("Nonsupport instruction of " + code.getName());
        }

    }

    public String get() {
        return result.toString();
    }

    public final Result getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }

    public StringBuilder getResult() {
        return result;
    }

    public Stack<Type> getOpStack() {
        return opStack;
    }

    public CodeAttr getCodeAttr() {
        return codeAttr;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public int[] getExceptionIndexes() {
        return exceptionIndexes;
    }

    public LocalVariableTable[] getLocalVariableTables() {
        return localVariableTables;
    }
}

