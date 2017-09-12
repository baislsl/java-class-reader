package com.baislsl.decompiler.javap;

import com.baislsl.decompiler.Constants;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.constantPool.ClassTag;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;

public class Javap {

    public static int getAccessFlag(Result clazz) {
        return clazz.getAccessFlag();
    }

    public static boolean isClass(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_INTERFACE) == 0;
    }

    public static boolean isInterface(Result clazz) {
        return !isClass(clazz);
    }

    public static String getClassName(Result clazz) throws DecompileException {
        int classIndex = clazz.getThisClass();
        ConstantPool classInfo = clazz.getConstantPool(classIndex);
        if (!(classInfo instanceof ClassTag))
            throw new DecompileException("Error format of class info");
        int index = ((ClassTag) classInfo).getNameIndex();
        return clazz.getUTF8Info(index);
    }

    /**
     * return direct super class name
     * for class return null if no super class
     * for interface, return null, too
     *
     * @param clazz
     * @return super class name
     * @throws DecompileException
     */
    public static String getSuperClassName(Result clazz) throws DecompileException {
        if (isInterface(clazz)) return null;

        int superIndex = clazz.getSuperClass();
        if (superIndex == 0) return null;
        ConstantPool classInfo = clazz.getConstantPool(superIndex);
        if (!(classInfo instanceof ClassTag))
            throw new DecompileException("Error format of class info");
        int index = ((ClassTag) classInfo).getNameIndex();
        return clazz.getUTF8Info(index);
    }

    public static String[] getInterfacesName(Result clazz) throws DecompileException {
        int count = clazz.getInterfacesCount();
        String[] ans = new String[count];
        int[] interfaceIndex = clazz.getInterfaces();
        for (int i = 0; i < count; i++) {
            ConstantPool interfaceInfo = clazz.getConstantPool(interfaceIndex[i]);
            if( !(interfaceInfo instanceof ClassTag))
                throw new DecompileException("Error format of class info");
            int nameIndex = ((ClassTag) interfaceInfo).getNameIndex();
            ans[i] = clazz.getUTF8Info(nameIndex);
        }
        return ans;
    }

}
