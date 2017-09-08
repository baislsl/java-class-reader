package com.baislsl.decompiler;

import com.baislsl.decompiler.structure.*;

/**
 * ClassFile {
 *      u4             magic;
 *      u2             minor_version;
 *      u2             major_version;
 *      u2             constant_pool_count;
 *      cp_info        constant_pool[constant_pool_count-1];
 *      u2             access_flags;
 *      u2             this_class;
 *      u2             super_class;
 *      u2             interfaces_count;
 *      u2             interfaces[interfaces_count];
 *      u2             fields_count;
 *      field_info     fields[fields_count];
 *      u2             methods_count;
 *      method_info    methods[methods_count];
 *      u2             attributes_count;
 *      attribute_info attributes[attributes_count];
 * }
 */

public class Result {
    private static final int DEFAULT_MAGIC = 0xcafebabe;
    private int magic;
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool[];
    private int accessFlags;
    private int thisClass;
    private int superClass;
    private Interface interfaces[];
    private Field fields[];
    private Method methods[];
    private Attribute attributes[];

    Result(int magic,
           int minorVersion,
           int majorVersion,
           ConstantPool[] constantPool,
           int accessFlags,
           int thisClass,
           int superClass,
           Interface[] interfaces,
           Field[] fields,
           Method[] methods,
           Attribute[] attributes) throws DecompileException{

        this.magic = magic;
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPool = constantPool;
        this.accessFlags = accessFlags;
        this.thisClass = thisClass;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
        this.attributes = attributes;

        // check magic bytes, which is always 0xCAFEBABE
        if(magic != DEFAULT_MAGIC)
            throw new DecompileException("Incorrect magic information, it seems that this is not a class file");

    }


}

