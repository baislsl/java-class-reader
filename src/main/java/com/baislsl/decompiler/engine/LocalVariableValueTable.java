package com.baislsl.decompiler.engine;

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

    public LocalValue get(int index) {
        return values[index];
    }

}

