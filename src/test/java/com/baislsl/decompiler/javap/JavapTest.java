package com.baislsl.decompiler.javap;

import com.baislsl.decompiler.ClassReader;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JavapTest {
    private final static Logger logger = LoggerFactory.getLogger(JavapTest.class);
    private static Result clazz = null, clazz2 = null, clazz3 = null;

    static {
        try {
            // clazz = ClassReader.decompile("src/test/resources/ClassTag.class");
            // clazz2 = ClassReader.decompile("src/test/resources/ConstantPoolBuilder.class");
            clazz3 = ClassReader.decompile("src/test/resources/TestClass.class");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getClassNameTest() throws DecompileException, IOException {
        logger.info("Class name = {}", Javap.getClassName(clazz));
    }

    @Test
    public void getSuperClassNameTest() throws DecompileException, IOException {
        logger.info("super class name = {}", Javap.getSuperClassName(clazz));
        logger.info("super class name = {}", Javap.getSuperClassName(clazz2));
    }

    @Test
    public void getInterfacesNameTest() throws DecompileException, IOException {
        String[] interfacesName = Javap.getInterfacesName(clazz);
        String[] interfacesName2 = Javap.getInterfacesName(clazz2);

        if (interfacesName.length == 0)
            logger.info("class {} has no interface", Javap.getClassName(clazz));
        if (interfacesName2.length == 0)
            logger.info("class {} has no interface", Javap.getClassName(clazz2));

        for (String name : interfacesName) {
            logger.info("class {} has interface : {}", Javap.getClassName(clazz), name);
        }
        for (String name : interfacesName2) {
            logger.info("class {} has interface : {}", Javap.getClassName(clazz2), name);
        }
    }


    @Test
    public void getContantPoolInfoTest() throws DecompileException, IOException {
        ConstantPool[] constantPools = clazz3.getConstantPools();
        for(ConstantPool cp : constantPools){
            if(cp == null) continue; // constantPools[0] is always null
            logger.info(Javap.getConstantPoolInfo(cp, clazz3));
        }
    }


}