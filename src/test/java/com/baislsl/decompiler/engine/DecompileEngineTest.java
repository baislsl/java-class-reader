package com.baislsl.decompiler.engine;

import com.baislsl.decompiler.ClassReader;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.Field;
import com.baislsl.decompiler.structure.Method;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class DecompileEngineTest {
    private final static Logger logger = LoggerFactory.getLogger(DecompileEngineTest.class);

    @Test
    public void decompileFieldTest() throws Exception {
        Result clazz = ClassReader.decompile("src/test/resources/TestClass.class");
        DecompileEngine engine = new DecompileEngine(clazz);
        for(Field field : clazz.getFields()){
            logger.info(engine.decompile(field));
        }
    }

    @Test
    public void decompileMethodTest() throws Exception {
        Result clazz = ClassReader.decompile("src/test/resources/TestClass.class");
        DecompileEngine engine = new DecompileEngine(clazz);
        for(Method method : clazz.getMethods()){
            logger.info(engine.decompile(method));
        }
    }

}