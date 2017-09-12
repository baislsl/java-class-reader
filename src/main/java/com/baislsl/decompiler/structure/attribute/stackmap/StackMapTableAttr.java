package com.baislsl.decompiler.structure.attribute.stackmap;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.attribute.Attribute;
import com.baislsl.decompiler.structure.attribute.AttributeBuilder;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;

public class StackMapTableAttr extends Attribute implements AttributeBuilder {
    private final static Logger logger = LoggerFactory.getLogger(StackMapTableAttr.class);
    private static final int ENTRY_NUMBER_SIZE = 2;
    private StackMapFrame[] entries;

    public StackMapTableAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int entryNumber = Read.readBytes(file, ENTRY_NUMBER_SIZE);
        entries = new StackMapFrame[entryNumber];
        for (int i = 0; i < entryNumber; i++) {
            entries[i] = StackMapFrame.getStackFrame(file);
            logger.info("Build stack map table of name {}", entries[i].getClass().getName());
        }
        return this;
    }

    public StackMapFrame[] getEntries() {
        return entries;
    }
}












