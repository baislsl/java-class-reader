package com.baislsl.decompiler;

import com.sun.org.apache.bcel.internal.classfile.Constant;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

import static com.sun.org.apache.bcel.internal.Constants.ILLEGAL_OPCODE;
import static com.sun.org.apache.bcel.internal.Constants.OPCODE_NAMES;

public class Temp {

    public static void main(String[] args) {
        for (int i = 0; i < 256; i++) {
            if(!OPCODE_NAMES[i].equals(ILLEGAL_OPCODE)){
                System.out.println(
"    public void _" + OPCODE_NAMES[i] + "(byte[] bytes) throws DecompileException {\n" +
        "        System.out.println(\"Not support instruction of \" + OPCODE_NAMES[Byte.toUnsignedInt(bytes[0]));\n" +
        "    }"
                );
            }
        }


    }
}
