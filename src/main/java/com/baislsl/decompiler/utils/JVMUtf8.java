package com.baislsl.decompiler.utils;

import com.baislsl.decompiler.DecompileException;

public class JVMUtf8 {
    private static final String ERROR_INFO = "Error format UTF-8 encode of JVM standard";

    public static String decode(byte[] msg) throws DecompileException {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < msg.length; i++) {
            byte b = msg[i];
            if ((b & 0b1000_0000) == 0) {    //  0b0xxx_xxxx
                str.append((char)(b & 0xff));
            } else if ((b & 0b1110_0000) == 0b1100_0000) {  //0b110x_xxxx
                if (i + 1 >= msg.length)
                    throw new DecompileException(ERROR_INFO);
                if ((msg[i + 1] & 0b1100_0000) != 0b1000_0000)
                    throw new DecompileException(ERROR_INFO);
                str.append((char)((b & 0x1f) << 6) + (msg[i + 1] & 0x3f));
                ++i;
            } else if ((b & 0b1111_0000) == 0b1110_0000) {
                if (i + 2 >= msg.length)
                    throw new DecompileException(ERROR_INFO);
                if ((msg[i + 1] & 0b1100_0000) != 0b1000_0000 || (msg[i + 2] & 0b1100_0000) != 0b1000_0000)
                    throw new DecompileException(ERROR_INFO);
                str.append((char)((b & 0xf) << 12) + ((msg[i + 1] & 0x3f) << 6) + (msg[i + 2] & 0x3f));
                i += 2;
            } else if (false) { // p86 如何区分这个和上面3
                i += 5;
            } else {
                throw new DecompileException(ERROR_INFO);
            }

        }

        return str.toString();
    }

}
