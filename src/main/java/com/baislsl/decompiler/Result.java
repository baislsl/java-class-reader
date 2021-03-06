package com.baislsl.decompiler;

import com.baislsl.decompiler.structure.*;
import com.baislsl.decompiler.structure.attribute.Attribute;
import com.baislsl.decompiler.structure.constantPool.ClassTag;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.Utf8Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassFile {
 * u4             magic;
 * u2             minor_version;
 * u2             major_version;
 * u2             constant_pool_count;
 * cp_info        constant_pool[constant_pool_count-1];
 * u2             access_flags;
 * u2             this_class;
 * u2             super_class;
 * u2             interfaces_count;
 * u2             interfaces[interfaces_count];
 * u2             fields_count;
 * field_info     fields[fields_count];
 * u2             methods_count;
 * method_info    methods[methods_count];
 * u2             attributes_count;
 * attribute_info attributes[attributes_count];
 * }
 */

public final class Result {
    private static final int DEFAULT_MAGIC = 0xcafebabe;
    private final int magic;
    private final int minorVersion;
    private final int majorVersion;
    private final ConstantPool constantPools[];
    private final int accessFlag;
    private final int thisClass;
    private final int superClass;
    private final int interfaces[];
    private final Field fields[];
    private final Method methods[];
    private final Attribute attributes[];

    Result(int magic,
           int minorVersion,
           int majorVersion,
           ConstantPool[] constantPool,
           int accessFlag,
           int thisClass,
           int superClass,
           int[] interfaces,
           Field[] fields,
           Method[] methods,
           Attribute[] attributes) throws DecompileException {

        this.magic = magic;
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPools = constantPool;
        this.accessFlag = accessFlag;
        this.thisClass = thisClass;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
        this.attributes = attributes;

        // check magic bytes, which is always 0xCAFEBABE
        if (magic != DEFAULT_MAGIC)
            throw new DecompileException("Incorrect magic number, it seems that this is not a class file");

    }

    public String getClassName() throws DecompileException {
        ClassTag classTag = (ClassTag) constantPools[thisClass];
        int index = classTag.getNameIndex();
        return getUTF8Info(index).replaceAll("/", ".");
    }

    public String getSimpleClassName() throws DecompileException {
        String name = getClassName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public String getSuperClassName() throws DecompileException {
        if (isInterface()) return null;

        ClassTag superClassTag = (ClassTag) constantPools[superClass];
        int index = superClassTag.getNameIndex();
        return getUTF8Info(index).replaceAll("/", ".");
    }

    public String getSimpleSuperClassName() throws DecompileException {
        String name = getSuperClassName();
        if (name == null) return null;
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public String[] getInterfacesName() throws DecompileException {
        List<String> ans  = new ArrayList<>(interfaces.length);
        for (int i : interfaces) {
            ClassTag interfaceTag = (ClassTag) constantPools[i];
            int nameIndex = interfaceTag.getNameIndex();
            ans.add(getUTF8Info(nameIndex));
        }
        return ans.toArray(new String[0]);
    }

    public String[] getSimpleInterfacesName() throws DecompileException {
        String[] ans = getInterfacesName();
        for (int i = 0; i < ans.length; i++) {
            ans[i] = ans[i].substring(ans[i].lastIndexOf(".") + 1);
        }
        return ans;
    }


    public boolean isPublic() {
        return (accessFlag & Constants.ACC_PUBLIC) != 0;
    }

    public boolean isFinal() {
        return (accessFlag & Constants.ACC_FINAL) != 0;
    }

    public boolean isSuper() {
        return (accessFlag & Constants.ACC_FINAL) != 0;
    }

    public boolean isClass() {
        return (accessFlag & Constants.ACC_INTERFACE) == 0;
    }

    public boolean isInterface() {
        return !isClass();
    }

    public boolean isAbstract() {
        return (accessFlag & Constants.ACC_ABSTRACT) != 0;
    }

    public boolean isAnnotation() {
        int flag = getAccessFlag();
        return (flag & Constants.ACC_ANNOTATION) != 0;
    }

    public boolean isEnum() {
        return (accessFlag & Constants.ACC_ENUM) != 0;
    }

    public String getUTF8Info(int index) throws DecompileException {
        ConstantPool constantPool = constantPools[index];
        if (!(constantPool instanceof Utf8Tag))
            throw new DecompileException(
                    String.format("constant pool at index %d is not a CONSTANT_Utf_info Structure", index)
            );
        return ((Utf8Tag) constantPool).getString();

    }

    public int getMagic() {
        return magic;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    /**
     * @return {String} "majorVersion.minorVersion"
     */
    public String getVersionName() {
        return Integer.toString(majorVersion) + "." + Integer.toString(minorVersion);
    }

    /**
     * @return {double} majorVersion.minorVersion
     */
    public double getVersion() {
        double ans = minorVersion;
        while (ans >= 1) ans /= 10;
        return ans + majorVersion;
    }

    public static int getDefaultMagic() {
        return DEFAULT_MAGIC;
    }

    public ConstantPool[] getConstantPools() {
        return constantPools;
    }

    public ConstantPool getConstantPool(int index) {
        return constantPools[index];
    }

    public int getAccessFlag() {
        return accessFlag;
    }

    public int getThisClass() {
        return thisClass;
    }

    public int getSuperClass() {
        return superClass;
    }

    public int[] getInterfaces() {
        return interfaces;
    }

    public int getInterfacesCount() {
        return interfaces.length;
    }

    public Field[] getFields() {
        return fields;
    }

    public Field getFieldAt(int index) {
        return fields[index];
    }

    public Method[] getMethods() {
        return methods;
    }

    public Method getMethodAt(int index) {
        return methods[index];
    }

    public Attribute[] getAttributes() {
        return attributes;
    }
}

