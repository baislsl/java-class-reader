package com.baislsl.decompiler;

import com.baislsl.decompiler.structure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;

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
        Interface interfaces[];
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
        constantPool = new ConstantPool[constantPoolCount];
        // ...

        //read access flags
        accessFlags = readBytes(file, SizeInfo.ACCESS_FLAGS_SIZE);
        logger.info("access flag = 0x{}", Integer.toHexString(accessFlags));

        // read this class
        thisClass = readBytes(file, SizeInfo.THIS_CLASS_SIZE);
        logger.info("this class = {}", thisClass & 0xff);

        // read super class
        superClass = readBytes(file, SizeInfo.SUPER_CLASS_SIZE);
        logger.info("super class = {}", superClass & 0xff);

        // read interfaces
        interfaceCount = readBytes(file, SizeInfo.INTERFACES_COUNT_SIZE);
        interfaces = new Interface[interfaceCount];
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

    private static int readBytes(DataInputStream file, int size) throws DecompileException {
        byte[] buffer = new byte[size];
        try {
            if(file.read(buffer) != size)
                throw new DecompileException("file format error");
        } catch (IOException e) {
            throw new DecompileException("file format error");
        }
        int result = 0;
        for (byte b : buffer) {
            result <<= 8;
            result |= 0xff & b;
        }
        return result;
    }
}
