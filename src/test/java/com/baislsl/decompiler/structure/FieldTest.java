package com.baislsl.decompiler.structure;

import com.baislsl.decompiler.ClassReader;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.attribute.CodeAttrTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class FieldTest {
    private final static Logger logger = LoggerFactory.getLogger(CodeAttrTest.class);

    @Test
    public void name() throws Exception {
        Result clazz = ClassReader.decompile("src/test/resources/TestClass.class");
        for(Field field : clazz.getFields()){
            logger.info(field.name());
        }
    }

}