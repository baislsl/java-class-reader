package com.baislsl.decompiler.engine;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.instruction.*;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.attribute.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Stream;


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
    private Executable[] executables;


    /**
     * from, to使用在解析循环中, 如果某步调到from, 解析为continue; 如果某步跳到to, 解析为break
     */
    private int from, to;

    private enum Flag {WHILE, DO_WHILE, IF, IF_ELSE}

    /**
     * @param variableValueTable use when need to inherited local value table
     *                           if null, constructor will construct a new local value table instead
     */
    public Frame(Result clazz, Method method, LocalVariableValueTable variableValueTable) throws DecompileException{
        this.clazz = clazz;
        this.method = method;
        this.opStack = new Stack<>();
        this.result = new StringBuilder();
        this.localVariableTables = variableValueTable;

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
        if(codeAttr == null)
            throw new DecompileException("A method must have and only have a code attribute");

        if(localVariableTables == null){    // do not offer local varibale table
            for (Attribute attribute : codeAttr.getAttributes()) {
                if (attribute instanceof LocalVariableTableAttr) {
                    this.localVariableTables = new LocalVariableValueTable(((LocalVariableTableAttr) attribute).getTables(), clazz, method);
                }
            }
        }

        executables = new Executable[codeAttr.getCodes().length];
        for (int i = 0; i < executables.length; i++) {
            executables[i] = codes[i].cast(this);
        }

    }

    public Frame exec(int from, int to) throws DecompileException{
        return exec(from, to, from, to);
    }
    /**
     * decompile code[from, to), the caller should ensure
     * that code[from, to) is complete
     *
     * @param from start index of the code to decompile
     * @param to   end index of the code to decompile
     * @param min min index of code, if the object of jump instruction is min, translate to continue
     * @param max max index of code, if the object of jump instruction is min, translate to break
     * @return decompiled frame
     */
    public Frame exec(int from, int to, int min, int max) throws DecompileException {
        System.out.println("hello");
        for (int i = to - 1; i >= from; i--) {
            if (executables[i] instanceof JumpInstruction &&
                    ((JumpInstruction) executables[i]).getOffset() < 0) {
                int begin = ((JumpInstruction) executables[i]).getFinalJumpObject();

                // checked in the parent call
                if(begin == from) continue;

                System.out.println(executables[i].getClass().getName());
                int end = i + 1;
                for (int j = begin; j < i; j++) {
                    if (executables[j] instanceof JumpInstruction) {
                        end = Math.max(end, ((JumpInstruction) executables[j]).getFinalJumpObject());
                    }
                }

                // checked in the parent call
                if(end == to) continue;

                exec(from, begin, min, max);
                result.append(
                        "while(true){\n" + new Frame(clazz, method, localVariableTables).exec(begin, end).get() + "\n}"
                );

                result.append(new Frame(clazz, method, localVariableTables).exec(end, to, min, max).get());
                return this;
            }
        }

        // just if, else operation, no loop
        this.from = min;
        this.to = max;
        if(from == 61) return this;
        for (int i = from; i < to; i++) {
            executables[i].exec();
        }

        return this;
    }

    /**
     * This method intends to run all the possible ways, and get all possible ways id run on each
     * instruction.
     * <p>
     * When run to a unmarked conditional jump instruction,
     * mark the instruction, and then branch into two runs according the jump operation.
     * When run to a marked conditional jump instruction or other instruction,
     * run to the next instruction.
     *
     * @param count count.get(i) record the way id will run on code[i]
     * @param from  from index of code
     * @param cur   current operation points
     * @param id    way is
     * @param flags mark flag for conditional jump
     * @param last  a virtual last code to collect all the final possible way id
     */
    private void travel(ArrayList<Set<Integer>> count, int from, int cur, int id,
                        boolean[] flags, Set<Integer> last)
            throws DecompileException {
        if (cur == from + count.size()) {
            last.add(id);
            return;
        }
        count.get(cur).add(id);

        if (!(executables[cur] instanceof ConditionalJumpInstruction)) {
            travel(count, from, cur + 1, id, flags, last);
        } else {
            count.get(cur).add(id + 1);
            if (!flags[cur]) {
                flags[cur] = true;
                travel(count, from, cur + 1, id, flags, last);
                travel(count, from, cur + ((JumpInstruction) executables[cur]).getOffset(),
                        id + 1, flags, last);
            } else {
                travel(count, from, cur + 1, id, flags, last);
            }
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


    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Code[] getCodes() {
        return codes;
    }

    public Executable[] getExecutables() {
        return executables;
    }

    public Executable getExecutableAt(int index){
        return executables[index];
    }
}

