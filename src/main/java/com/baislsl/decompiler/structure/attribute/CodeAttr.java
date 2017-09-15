package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public class CodeAttr extends Attribute implements AttributeBuilder {
    private static final Logger logger = LoggerFactory.getLogger(CodeAttr.class);
    private static final int MAX_STACK_SIZE = 2;
    private static final int MAX_LOCALS_SIZE = 2;
    private static final int CODE_LENGTH_SIZE = 4;
    private static final int EXCEPTION_TABLE_LENGTH_SIZE = 2;
    private static final int ATTRIBUTE_COUNT_SIZE = 2;

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

        // read codes
        int codeLength = Read.readBytes(file, CODE_LENGTH_SIZE);
        int read = 0;
        List<Code> codes = new ArrayList<>();
        while(read != codeLength){
            Code code = Code.build(file);
            codes.add(code);
//            logger.info("read in code : {}", code.toString());
            read += code.getSize();
        }
        this.codes = codes.toArray(new Code[0]);

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
        for (int i = 0; i < attributeCount; i++) {
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

    @Override
    public String name() throws DecompileException {
        StringBuilder ans = new StringBuilder();
        ans.append("stack=").append(maxStack).append(", ")
                .append("locals=").append(maxLocals).append("\n");
        int lineNumber = 0;
        for(Code code : codes){
            ans.append("    ").append(lineNumber).append(": ").append(code.name()).append("\n");
            lineNumber += code.getSize();
        }
        for(Attribute attribute : attributes){
            ans.append(attribute.getClass().getName())
                    .append("\n")
                    .append(attribute.name())
                    .append("\n");
        }
        return ans.toString();
    }
}

class ExceptionTable {
    public static final int PC_SIZE = 2;
    public static final int CATCH_TYPE_SIZE = 2;
    public int startPC, endPC;
    public int handlerPC;
    public int catchType;

    public ExceptionTable() {
    }

}