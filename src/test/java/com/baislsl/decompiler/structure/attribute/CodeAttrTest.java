package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.ClassReader;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.javap.Javap;
import com.baislsl.decompiler.structure.Method;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class CodeAttrTest {

    private final static Logger logger = LoggerFactory.getLogger(CodeAttrTest.class);

    @Test
    public void nameTest() throws DecompileException, IOException {
        Result clazz = ClassReader.decompile("src/test/resources/TestClass.class");
        for(Method method : clazz.getMethods()){
            logger.info(method.name());

            for(Attribute attribute : method.getAttributes()){
                if(attribute instanceof CodeAttr){
                    logger.info(attribute.name());
                }
            }
        }


    }

}