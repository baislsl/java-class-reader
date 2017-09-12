package com.baislsl.decompiler;

import com.baislsl.decompiler.structure.*;
import com.baislsl.decompiler.structure.attribute.Attribute;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.Utf8Tag;

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

    public int getInterfacesCount(){
        return interfaces.length;
    }

    public Field[] getFields() {
        return fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public Method getMethod(int index){
        return methods[index];
    }

    public Attribute[] getAttributes() {
        return attributes;
    }
}

