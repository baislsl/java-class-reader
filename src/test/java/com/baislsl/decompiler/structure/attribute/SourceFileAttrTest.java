package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.ClassReader;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class SourceFileAttrTest {
    private final static Logger logger = LoggerFactory.getLogger(SourceFileAttrTest.class);

    @Test
    public void nameTest() throws DecompileException, IOException {
        Result clazz = ClassReader.decompile("src/test/resources/TestClass.class");
        for(Attribute attribute : clazz.getAttributes()){
            if(attribute instanceof SourceFileAttr){
                logger.info(attribute.name());
            }
        }

    }
}