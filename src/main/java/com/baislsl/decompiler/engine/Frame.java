package com.baislsl.decompiler.engine;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.instruction.Executable;
import com.baislsl.decompiler.instruction.Instruction;
import com.baislsl.decompiler.instruction.JumpInstruction;
import com.baislsl.decompiler.instruction.UnconditionalJumpInstruction;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.attribute.*;

import java.lang.reflect.Constructor;
import java.util.Stack;


public class Frame {
    private Result clazz;
    private Method method;
    private StringBuilder result;
    private Stack<Value> opStack;
    private CodeAttr codeAttr;
    private Code[] codes;
    private LocalVariableValueTable localVariableTables;
    private int[] exceptionIndexes;
    private Parameter[] parameters;

    private enum Flag {WHILE, DO_WHILE, IF, IF_ELSE}

    ;


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
                    this.localVariableTables = new LocalVariableValueTable(((LocalVariableTableAttr) attribute).getTables(), clazz);
                }
            }
        }
    }

    public Frame exec() throws DecompileException {
        for (int i = 0; i < codes.length; i++) {
            Executable executableCode = getExecutableCode(codes[i]);
            if (executableCode == null) continue;

            if (!(executableCode instanceof JumpInstruction)) {
                executableCode.exec();
                continue;
                ;
            }

            Flag flag = Flag.IF;
            int subIndex = i;
            int subOffset = 0;
            int offset = ((JumpInstruction) executableCode).getOffset();
            if (offset < 0) { // do {} while loop
                flag = Flag.DO_WHILE;
            } else {
                for (int j = i; j < i + offset; j++) {
                    Executable exec = getExecutableCode(codes[j]);
                    if (exec instanceof UnconditionalJumpInstruction) {
                        subOffset = ((JumpInstruction) exec).getOffset();

                        if (subOffset + j < i) {
                            // while(){ ] loop
                            flag = Flag.WHILE;
                            subIndex = j;

                        } else if (subOffset + j < i + offset) {
                            // continue

                        } else { // suboffset + j >= i + offset
                            // if {] else {] loop
                            flag = Flag.IF_ELSE;
                            subIndex = j;
                        }


                    }
                } // end of "for(int j = i; j < i + offset;j++)"
            }

            switch (flag) {
                case IF:
                    i += ifLoop(i, offset);
                    continue;
                case WHILE:
                    i += whileLoop(i, offset, subIndex, subOffset);
                    continue;
                case IF_ELSE:
                    i += ifElseLoop(i, offset, subIndex, subOffset);
                    continue;
                case DO_WHILE:

            }


        }

        return this;
    }

    private int ifLoop(int cur, int offset) {

        return offset;
    }


    private Executable getExecutableCode(Code code) throws DecompileException {
        String instructionClassPath = Instruction.class.getName();
        try {
            Class cl = Class.forName(
                    instructionClassPath.substring(0, instructionClassPath.length() - "Instruction".length())
                            + code.getName().toUpperCase()
            );
            Constructor constructor = cl.getConstructor();
            Instruction instruction = (Instruction) constructor.newInstance();
            return instruction.build(code, this);
        } catch (ReflectiveOperationException e) {
            // throw new DecompileException(e);
            System.out.println("Nonsupport instruction of " + code.getName());
        }
        return null;
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

    public Stack<Value> getOpStack() {
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

    public LocalVariableValueTable getLocalVariableTables() {
        return localVariableTables;
    }
}

