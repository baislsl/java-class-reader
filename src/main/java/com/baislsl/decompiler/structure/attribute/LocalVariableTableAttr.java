package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.Name;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class LocalVariableTableAttr extends Attribute implements AttributeBuilder {
    private static final int LOCAL_VARIABLE_TABLE_LEGNTH_SIZE = 2;
    private LocalVariableTable[] tables;

    public LocalVariableTableAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int localVariableTableLength = Read.readBytes(file, LOCAL_VARIABLE_TABLE_LEGNTH_SIZE);
        tables = new LocalVariableTable[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            tables[i] = new LocalVariableTable();
            tables[i].startPC = Read.readBytes(file, LocalVariableTable.START_PC_SIZE);
            tables[i].length = Read.readBytes(file, LocalVariableTable.LENGTH_SIZE);
            tables[i].nameIndex = Read.readBytes(file, LocalVariableTable.INDEX_SIZE);
            tables[i].descriptorIndex = Read.readBytes(file, LocalVariableTable.INDEX_SIZE);
            tables[i].index = Read.readBytes(file, LocalVariableTable.INDEX_SIZE);
        }
        return this;
    }

    public LocalVariableTable[] getTables() {
        return tables;
    }

    @Override
    public String name() throws DecompileException {
        StringBuilder ans = new StringBuilder();
        for(LocalVariableTable table : tables){
            ans.append("start=").append(table.startPC).append(", ")
                    .append("length=").append(table.length).append(", ")
                    .append("name=").append(constantPools[table.nameIndex].name()).append(",")
                    .append("signature=").append(constantPools[table.descriptorIndex].name()).append(", ")
                    .append("\n");
        }
        return ans.toString();
    }
}
