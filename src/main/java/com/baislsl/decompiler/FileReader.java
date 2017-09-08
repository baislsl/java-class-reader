package com.baislsl.decompiler;

import com.baislsl.decompiler.structure.constantPool.ConstantPoolBuilder;
import com.baislsl.decompiler.structure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;

import static com.baislsl.decompiler.utils.Read.readBytes;

public class FileReader implements Reader {
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

    public Result decompile(DataInputStream file) throws DecompileException {
        int magic;
        int minorVersion;
        int majorVersion;
        int constantPoolCount;
        ConstantPool constantPool[];
        int accessFlags;
        int thisClass;
        int superClass;
        int interfaceCount;
        int interfaces[];
        int fieldCount;
        Field fields[];
        int methodCount;
        Method methods[];
        int attributeCount;
        Attribute attributes[];

        // read magic
        magic = readBytes(file, SizeInfo.MAGIC_SIZE);
        logger.info("magic = {}", Integer.toHexString(magic));

        // read minor version
        minorVersion = readBytes(file, SizeInfo.MINOR_VERSION_SIZE);
        logger.info("minor version = {}", minorVersion);

        // read major version
        majorVersion = readBytes(file, SizeInfo.MAJOR_VERSION_SIZE);
        logger.info("major version = {}", majorVersion);

        //read constant pool
        constantPoolCount = readBytes(file, SizeInfo.CONSTANT_POOL_COUNT_SIZE);
        logger.info("constant pool count = {}", constantPoolCount);
        constantPool = buildConstantPool(file, constantPoolCount);

        //read access flags
        accessFlags = readBytes(file, SizeInfo.ACCESS_FLAGS_SIZE);
        logger.info("access flag = 0x{}", Integer.toHexString(accessFlags));

        // read this class
        thisClass = readBytes(file, SizeInfo.THIS_CLASS_SIZE);
        logger.info("this class = {}", thisClass);

        // read super class
        superClass = readBytes(file, SizeInfo.SUPER_CLASS_SIZE);
        logger.info("super class = {}", superClass);

        // read interfaces
        interfaceCount = readBytes(file, SizeInfo.INTERFACES_COUNT_SIZE);
        interfaces = buildInterfaces(file, interfaceCount);
        // ...

        // read fields
        fieldCount = readBytes(file, SizeInfo.FIELDS_COUNT_SIZE);
        fields = new Field[fieldCount];
        // ...

        // read method
        methodCount = readBytes(file, SizeInfo.METHODS_COUNT_SIZE);
        methods = new Method[methodCount];
        // ...

        // read attribute
        attributeCount = readBytes(file, SizeInfo.ATTRIBUTES_COUNT_SIZE);
        attributes = new Attribute[attributeCount];

        return new Result(
                magic,
                minorVersion,
                majorVersion,
                constantPool,
                accessFlags,
                thisClass,
                superClass,
                interfaces,
                fields,
                methods,
                attributes
        );
    }

    private static ConstantPool[] buildConstantPool(DataInputStream file, int constantPoolCount)
            throws DecompileException {
        ConstantPool[] constantPools = new ConstantPool[constantPoolCount];

        // note that the constant pool table is indexed from 1 to constantPoolCount - 1.
        // constantPools[0] is null
        for (int i = 1; i < constantPoolCount; i++) {
            int tag = readBytes(file, ConstantPool.TAG_SIZE);
            ConstantPoolBuilder constantPoolBuilder = ConstantPool.getConstantPoolBuilder(tag);
            constantPools[i] = constantPoolBuilder.build(file);
            logger.info("build constant pool of type : {}", constantPools[i].getClass().getName());
        }

        return constantPools;
    }

    private static int[] buildInterfaces(DataInputStream file, int interfaceCount)
            throws DecompileException {
        int[] interfaces = new int[interfaceCount];
        for (int i = 0; i < interfaceCount; i++) {
            interfaces[i] = readBytes(file, SizeInfo.INTERFACES_SIZE);
            logger.info("build interfaces of value : {}", interfaces[i]);
        }
        return interfaces;
    }

}
