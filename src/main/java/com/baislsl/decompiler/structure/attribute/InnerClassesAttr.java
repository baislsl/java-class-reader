package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public class InnerClassesAttr extends Attribute implements AttributeBuilder {
    private static final int CLASS_NUMBER_SIZE = 2;

    private ClassInfo[] classes;

    public InnerClassesAttr(int attributeNameIndex, int attributeLength) {
        super(attributeNameIndex, attributeLength);
    }

    @Override
    public Attribute build(DataInputStream file, ConstantPool[] constantPools) throws DecompileException {
        int classNumber = Read.readBytes(file, CLASS_NUMBER_SIZE);
        classes = new ClassInfo[classNumber];
        for (int i = 0; i < classNumber; i++) {
            classes[i] = new ClassInfo();
            classes[i].innerClassInfoIndex = Read.readBytes(file, ClassInfo.INDEX_SIZE);
            classes[i].outerClassInfoIndex = Read.readBytes(file, ClassInfo.INDEX_SIZE);
            classes[i].innerNameIndex = Read.readBytes(file, ClassInfo.INDEX_SIZE);
            classes[i].innerClassAccessFlag = Read.readBytes(file, ClassInfo.FLAG_SIZE);
        }
        return this;
    }

    public ClassInfo[] getClasses() {
        return classes;
    }
}

class ClassInfo {
    public static final int INDEX_SIZE = 2;
    public static final int FLAG_SIZE = 2;
    int innerClassInfoIndex, outerClassInfoIndex;
    int innerNameIndex;
    int innerClassAccessFlag;

}
