package com.baislsl.decompiler.utils;

import com.baislsl.decompiler.ClassReader;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.Method;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JavapTest {
    private final static Logger logger = LoggerFactory.getLogger(JavapTest.class);
    public static Result clazz = null, clazz2 = null, clazz3 = null;

    static {
        try {
            clazz = ClassReader.decompile("src/test/resources/ClassTag.class");
            clazz2 = ClassReader.decompile("src/test/resources/ConstantPoolBuilder.class");
            clazz3 = ClassReader.decompile("src/test/resources/TestClass.class");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getClassNameTest() throws DecompileException, IOException {
        logger.info("Class name = {}", clazz.getClassName());
    }

    @Test
    public void getSuperClassNameTest() throws DecompileException, IOException {
        logger.info("super class name = {}", clazz.getSuperClassName());
        logger.info("super class name = {}", clazz2.getSuperClassName());
    }

    @Test
    public void getInterfacesNameTest() throws DecompileException, IOException {
        String[] interfacesName = clazz.getInterfacesName();
        String[] interfacesName2 = clazz2.getInterfacesName();

        if (interfacesName.length == 0)
            logger.info("class {} has no interface", clazz.getClassName());
        if (interfacesName2.length == 0)
            logger.info("class {} has no interface", clazz2.getClassName());

        for (String name : interfacesName) {
            logger.info("class {} has interface : {}", clazz.getClassName(), name);
        }
        for (String name : interfacesName2) {
            logger.info("class {} has interface : {}", clazz.getClassName(), name);
        }
    }


    @Test
    public void getContantPoolInfoTest() throws DecompileException, IOException {
        ConstantPool[] constantPools = clazz3.getConstantPools();
        for (ConstantPool cp : constantPools) {
            if (cp == null) continue; // constantPools[0] is always null
            logger.info(Javap.getConstantPoolInfo(cp, clazz3));
        }
    }

    @Test
    public void getFieldDescriptionTest() throws DecompileException {
        for(Field field : clazz3.getFields()){
            logger.info(field.name());
        }
    }

    @Test
    public void getMethodDescriptionTest() throws DecompileException{
        for(Method method : clazz3.getMethods()){
            logger.info(method.name());
        }
    }
}