package com.baislsl.decompiler.engine;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.attribute.LocalVariableTable;

public class LocalVariableValueTable {

    private LocalValue[] values;


    public LocalVariableValueTable(LocalVariableTable[] localVariableTables, Result clazz) {
        values = new LocalValue[localVariableTables.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = new LocalValue(localVariableTables[i], clazz);
        }
    }

    public LocalValue get(int slot, int lineIndex) throws DecompileException {
        for (LocalValue value : values) {
            if (value.index() == slot
                    && value.startPC() <= lineIndex
                    && value.startPC() + value.length() > lineIndex) {
                return value;
            }
        }
        throw new DecompileException(
                String.format("Can not find value at slot %d with line index %d in local value table",
                        slot, lineIndex)
        );
    }

}

