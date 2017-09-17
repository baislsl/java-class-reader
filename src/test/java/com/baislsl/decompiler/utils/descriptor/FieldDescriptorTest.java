package com.baislsl.decompiler.utils.descriptor;

import com.baislsl.decompiler.DecompileException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldDescriptorTest {
    private final static Logger logger = LoggerFactory.getLogger(FieldDescriptorTest.class);
    @Test
    public void test() throws DecompileException{
        String[] testcases ={
                "B", "[[F", "BC[[DF[JSZ", "[[BC[[Ljava.util.List;J[S"
        };


        for(String testcase : testcases){
            logger.info("reading : {}", testcase);
            FieldDescriptor[] fieldDescriptors = FieldDescriptor.getFieldDescriptors(testcase);
            logger.info("result");
            for(FieldDescriptor fieldDescriptor : fieldDescriptors){
                logger.info(fieldDescriptor.toString());
            }
        }
    }
}