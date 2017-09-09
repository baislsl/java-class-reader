package com.baislsl.decompiler.utils;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.FileReaderTest;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.structure.constantPool.Utf8Tag;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JVMUtf8Test {
    private static final Logger logger = LoggerFactory.getLogger(JVMUtf8Test.class);
    @Test
    public void test() throws DecompileException{
        Result result = FileReaderTest.runFile("src/test/resources/ClassTag.class");
        Assert.assertNotNull(result);
        ConstantPool[] constantPools = result.getConstantPool();
        Assert.assertNotNull(constantPools);
        for(ConstantPool constantPool : constantPools){
            if(constantPool instanceof Utf8Tag){
                String msg = JVMUtf8.decode(((Utf8Tag)constantPool).getBytes());
                logger.info(msg);
            }
        }

    }
}
