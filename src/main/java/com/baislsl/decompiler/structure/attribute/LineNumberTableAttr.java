package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class LineNumberTableAttr extends Attribute implements AttributeBuilder {
    private static final int LINE_NUMBER_TABLE_LENGTH_SIZE = 2;
    private LineNumberTable[] lineNumberTables;

    public LineNumberTableAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int lineNumberTableLength = Read.readBytes(file, LINE_NUMBER_TABLE_LENGTH_SIZE);
        lineNumberTables = new LineNumberTable[lineNumberTableLength];
        for (int i = 0; i < lineNumberTableLength; i++) {
            lineNumberTables[i] = new LineNumberTable();
            lineNumberTables[i].startPC = Read.readBytes(file, LineNumberTable.START_PC_SIZE);
            lineNumberTables[i].lineNumber = Read.readBytes(file, LineNumberTable.LINE_NUMBER_SIZE);
        }
        return this;
    }

    public LineNumberTable[] getLineNumberTables() {
        return lineNumberTables;
    }

    @Override
    public String name() throws DecompileException {
        StringBuilder ans = new StringBuilder();
        for (LineNumberTable table : lineNumberTables) {
            ans.append("line ")
                    .append(table.lineNumber)
                    .append(": ")
                    .append(table.startPC)
                    .append("\n");
        }
        return ans.toString();
    }
}

class LineNumberTable {
    public static final int START_PC_SIZE = 2;
    public static final int LINE_NUMBER_SIZE = 2;
    int startPC;
    int lineNumber;
}
