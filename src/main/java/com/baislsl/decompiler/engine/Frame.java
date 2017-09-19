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

    private enum Flag {WHILE, DO_WHILE, IF, IF_ELSE}

    public Frame(Result clazz, Method method) throws DecompileException {
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

        executables = new Executable[codeAttr.getCodes().length];
        for (int i = 0; i < executables.length; i++) {
            executables[i] = codes[i].cast(this);
        }
    }

    /**
     * decompile code[from, to), the caller should ensure
     * that code[from, to) is complete
     *
     * @param from start index of the code to decompile
     * @param to   end index of the code to decompile
     * @return decompiled frame
     */
    public Frame exec(int from, int to) throws DecompileException {
        // add a virtual last code
        ArrayList<Set<Integer>> count = new ArrayList<>(to - from + 1);
        Set<Integer> last = new HashSet<>();
        boolean[] flags = new boolean[to - from + 1];
        travel(count, from, from, 0, flags, last);

        if (last.size() == 1) {
            // only one branch
            // no any conditional jump instruction

        } else {
            int cur = from;
            int curMax = count.get(cur).size();
            while (curMax == count.get(cur).size()) {
                ++cur;
            }

            while (curMax != count.get(cur).size()) {
                for (int i : count.get(cur)) {
                    curMax = Math.max(curMax, i);
                }
            }

            oneloop(from, cur);
            result.append(new Frame(clazz, method).exec(cur, to).result);
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
     * @param from from index of code
     * @param cur current operation points
     * @param id way is
     * @param flags mark flag for conditional jump
     * @param last a virtual last code to collect all the final possible way id
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
}

