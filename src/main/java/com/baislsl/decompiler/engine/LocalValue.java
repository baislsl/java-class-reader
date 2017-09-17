package com.baislsl.decompiler.engine;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.attribute.LocalVariableTable;

public class LocalValue {
    private final LocalVariableTable table;
    private Value value;
    boolean access;

    LocalValue(LocalVariableTable table, Result clazz) {
        this.table = table;
        try {
            this.value = new Value(clazz.getUTF8Info(table.nameIndex));
        } catch (DecompileException e) {
            e.printStackTrace();
        }
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Value value() {
        return value;
    }

    public int startPC() {
        return table.startPC;
    }

    public int length() {
        return table.length;
    }

    public int nameIndex() {
        return table.nameIndex;
    }

    public int descriptorIndex() {
        return table.descriptorIndex;
    }

    public int index() {
        return table.index;
    }


}
