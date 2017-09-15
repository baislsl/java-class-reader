package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class LocalVariableTypeTableAttr extends Attribute implements AttributeBuilder {
    private final static int LOCAL_VARIABLE_TYPE_TABLE_LENGTH_SIZE = 2;
    LocalVariableTypeTable[] tables;

    public LocalVariableTypeTableAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int localAttributeTypeTableLength = Read.readBytes(file, LOCAL_VARIABLE_TYPE_TABLE_LENGTH_SIZE);
        tables = new LocalVariableTypeTable[localAttributeTypeTableLength];
        for (int i = 0; i < localAttributeTypeTableLength; i++) {
            tables[i] = new LocalVariableTypeTable();
            tables[i].startPC = Read.readBytes(file, LocalVariableTypeTable.START_PC_SIZE);
            tables[i].length = Read.readBytes(file, LocalVariableTypeTable.LENGTH_SIZE);
            tables[i].nameIndex = Read.readBytes(file, LocalVariableTypeTable.INDEX_SIZE);
            tables[i].signatureIndex = Read.readBytes(file, LocalVariableTypeTable.INDEX_SIZE);
            tables[i].index = Read.readBytes(file, LocalVariableTypeTable.INDEX_SIZE);
        }
        return this;
    }

    @Override
    public String name() throws DecompileException {
        StringBuilder ans = new StringBuilder();
        for(LocalVariableTypeTable table : tables){
            ans.append("start=").append(table.startPC).append(", ")
                    .append("length=").append(table.length).append(", ")
                    .append("name=").append(constantPools[table.nameIndex].name()).append(",")
                    .append("signature=").append(constantPools[table.signatureIndex].name()).append(", ")
                    .append("\n");
        }
        return ans.toString();
    }
}

class LocalVariableTypeTable {
    public static final int START_PC_SIZE = 2;
    public static final int LENGTH_SIZE = 2;
    public static final int INDEX_SIZE = 2;

    public int startPC;
    public int length;
    public int nameIndex;
    public int signatureIndex;
    public int index;
}
