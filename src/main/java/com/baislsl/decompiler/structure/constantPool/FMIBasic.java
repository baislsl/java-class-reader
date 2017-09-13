package com.baislsl.decompiler.structure.constantPool;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

/**
 * this class implement the function of
 *      CONSTANT_Fieldref_info
 *      CONSTANT_Methodref_info ,
 *      CONSTANT_InterfaceMethodref_info
 * Structures
 */
public abstract class FMIBasic extends ConstantPool {
    private static final int CLASS_INDEX_SIZE = 2;
    private static final int NAME_AND_TYPE_INDEX_SIZE = 2;

    private int classIndex;
    private int nameAndTypeIndex;

    FMIBasic(int tag) {
        super(tag);
    }

    @Override
    public ConstantPool build(DataInputStream file) throws DecompileException {
        classIndex = Read.readBytes(file, CLASS_INDEX_SIZE);
        nameAndTypeIndex = Read.readBytes(file, NAME_AND_TYPE_INDEX_SIZE);
        return this;
    }

    @Override
    public String[] description(Result result) throws DecompileException {
        int index = getNameAndTypeIndex();

        // result.getConstantPool(index) must be instance of NameAndTypeTag
        String[] nameAndType = result.getConstantPool(index).description(result);
        return new String[]{
                "#" + Integer.toString(getClassIndex()) + ".#" + Integer.toString(getNameAndTypeIndex()),
                nameAndType[2], nameAndType[3]
        };
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }
}
