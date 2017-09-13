package com.baislsl.decompiler.javap;

import com.baislsl.decompiler.Constants;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.constantPool.*;

public class Javap {

    public static String getConstantPoolInfo(ConstantPool cp, Result clazz)
            throws DecompileException {
        String[] cpDescription = cp.description(clazz);
        StringBuilder ans = new StringBuilder();
        if (cpDescription.length <= 3) {
            for (String s : cpDescription) {
                ans.append(s);
                ans.append("  ");
            }
        } else {
            ans.append(cpDescription[0]);
            ans.append("  ");
            ans.append(cpDescription[1]);
            ans.append("  ");
            for (int i = 2; i < cpDescription.length; i++) {
                ans.append(cpDescription[i]);
                if(i != cpDescription.length - 1) ans.append(":");
            }
        }
        return ans.toString();
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
            if (!(interfaceInfo instanceof ClassTag))
                throw new DecompileException("Error format of class info");
            int nameIndex = ((ClassTag) interfaceInfo).getNameIndex();
            ans[i] = clazz.getUTF8Info(nameIndex);
        }
        return ans;
    }


    public static int getAccessFlag(Result clazz) {
        return clazz.getAccessFlag();
    }

    public static boolean isPublic(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_PUBLIC) != 0;
    }

    public static boolean isFinal(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_FINAL) != 0;
    }

    public static boolean isSuper(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_FINAL) != 0;
    }

    public static boolean isClass(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_INTERFACE) == 0;
    }

    public static boolean isInterface(Result clazz) {
        return !isClass(clazz);
    }

    public static boolean isAbstract(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_ABSTRACT) != 0;
    }

    public static boolean isSynthetic(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_SYNTHETIC) != 0;
    }

    public static boolean isAnnotation(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_ANNOTATION) != 0;
    }

    public static boolean isEnum(Result clazz) {
        int flag = clazz.getAccessFlag();
        return (flag & Constants.ACC_ENUM) != 0;
    }
}
