package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class CodeAttr extends Attribute implements AttributeBuilder {
    private final static int MAX_STACK_SIZE = 2;
    private final static int MAX_LOCALS_SIZE = 2;
    private final static int CODE_LENGTH_SIZE = 4;
    private final static int EXCEPTION_TABLE_LENGTH_SIZE = 2;
    private final static int ATTRIBUTE_COUNT_SIZE = 2;

    private int maxStack;
    private int maxLocals;

    private Code[] codes;
    private ExceptionTable[] exceptionTables;
    private Attribute[] attributes;

    public CodeAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        maxStack = Read.readBytes(file, MAX_STACK_SIZE);
        maxLocals = Read.readBytes(file, MAX_LOCALS_SIZE);
        int CodeLength = Read.readBytes(file, CODE_LENGTH_SIZE);
        codes = new Code[CodeLength];
        // read codes
        // ...

        // read exception table
        int exceptionTablesLength = Read.readBytes(file, EXCEPTION_TABLE_LENGTH_SIZE);
        exceptionTables = new ExceptionTable[exceptionTablesLength];
        for (int i = 0; i < exceptionTablesLength; i++) {
            exceptionTables[i] = new ExceptionTable();
            exceptionTables[i].startPC = Read.readBytes(file, ExceptionTable.PC_SIZE);
            exceptionTables[i].endPC = Read.readBytes(file, ExceptionTable.PC_SIZE);
            exceptionTables[i].handlerPC = Read.readBytes(file, ExceptionTable.PC_SIZE);
            exceptionTables[i].catchType = Read.readBytes(file, ExceptionTable.CATCH_TYPE_SIZE);
        }

        // read attribute
        int attributeCount = Read.readBytes(file, ATTRIBUTE_COUNT_SIZE);
        attributes = new Attribute[attributeCount];
        for(int i = 0;i<attributeCount;i++){
            attributes[i] = Attribute.getAttribute(file, constantPools);
        }

        return this;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public Code[] getCodes() {
        return codes;
    }

    public ExceptionTable[] getExceptionTables() {
        return exceptionTables;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }
}
