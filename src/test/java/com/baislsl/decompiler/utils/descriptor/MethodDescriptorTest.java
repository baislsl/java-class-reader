package com.baislsl.decompiler.utils.descriptor;

import com.baislsl.decompiler.DecompileException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class MethodDescriptorTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodDescriptorTest.class);

    @Test
    public void test() throws DecompileException {
        String[] testcases = {
                "()V", "(B[[CLjava/util/List;[Z)[J", "(IDLjava/lang/Thread;)Ljava/lang/Object;"
        };

        for (String testcase : testcases) {
            logger.info("reading : {}", testcase);
            MethodDescriptor methodDescriptor = new MethodDescriptor(testcase);
            assertNotNull(methodDescriptor);

            logger.info("result");
            Descriptor[] descriptors = methodDescriptor.getParamDescriptors();
            for (Descriptor descriptor : descriptors) {
                logger.info("param : {}", descriptor.toString());
            }
            logger.info("return : {}", methodDescriptor.getReturnDescriptor().toString());

        }
    }

}