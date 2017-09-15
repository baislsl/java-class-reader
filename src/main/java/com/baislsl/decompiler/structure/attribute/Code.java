package com.baislsl.decompiler.structure.attribute;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.Name;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

import static com.sun.org.apache.bcel.internal.Constants.ILLEGAL_OPCODE;
import static com.sun.org.apache.bcel.internal.Constants.NO_OF_OPERANDS;
import static com.sun.org.apache.bcel.internal.Constants.OPCODE_NAMES;

/**
 * 虽然是造轮子， 但是jvm的指令实在多，我这里为了方便直接使用com.sun.org.apache.bcel.internal.Constants提供的
 * 关于指令的几个表格
 */
public class Code implements Name {
    private byte[] bytes;
    private String name;

    private Code() {
    }

    public static Code build(DataInputStream file) throws DecompileException {
        int op = Read.readBytes(file, 1);
        Code code = new Code();
        code.name = OPCODE_NAMES[op];
        if (code.name.equals(ILLEGAL_OPCODE))
            throw new DecompileException("Illegal opcode");
        int noOfOperands = NO_OF_OPERANDS[op];
        code.bytes = new byte[noOfOperands + 1];
        code.bytes[0] = (byte) op;
        if (noOfOperands != 0)
            Read.readBytes(file, code.bytes, 1);
        return code;
    }

    @Override
    public String toString() {
        switch (bytes.length) {
            case 1:
                return name;
            case 2:
                return name + " " + Byte.toUnsignedInt(bytes[1]);
            case 3:
                if (bytes[1] == 0)
                    return name + " #" + Byte.toUnsignedInt(bytes[2]);
                else
                    return name + " " + Integer.toString((Byte.toUnsignedInt(bytes[1]) << 8) | Byte.toUnsignedInt(bytes[2]));
            default:
                StringBuilder ans = new StringBuilder(name);
                for (int i = 1; i < bytes.length; i++) {
                    ans.append(" ");
                    ans.append(Byte.toString(bytes[i]));
                }
                return ans.toString();
        }
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getSize() {
        return bytes.length;
    }

    @Override
    public String name() throws DecompileException {
        return toString();
    }
}
