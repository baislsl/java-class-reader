package com.baislsl.decompiler.instruction;

import com.baislsl.decompiler.DecompileException;

public abstract class JumpInstruction extends Instruction {
    public int getOffset() throws DecompileException {
        int length = 0;
        if (bytes.length == 2) {
            length = get1();
        } else if (bytes.length == 3) {
            length = get2();
        } else if (bytes.length == 5) {
            length = Byte.toUnsignedInt(bytes[4]) << 24
                    | Byte.toUnsignedInt(bytes[3]) << 16
                    | Byte.toUnsignedInt(bytes[2]) << 8
                    | Byte.toUnsignedInt(bytes[1]);
        } else {
            throw new DecompileException("Error jump offset length");
        }
        System.out.println(this.getClass().getName() + length);
        int i = getCurrentIndex();
        int offset = 0;
        if (length < 0) {
            while (length != 0) {
                i--;
                offset--;
                length += frame.getCodes()[i].getSize();
            }
        } else {
            do {
                i++;
                offset++;
                length -= frame.getCodes()[i].getSize();
            } while (length != 0);
        }
        return offset;
    }

    public int getCurrentIndex() throws DecompileException {
        for (int i = 0; i < frame.getExecutables().length; i++) {
            if (this.equals(frame.getExecutables()[i]))
                return i;
        }
        return -1;
    }

    /**
     * @return current_address + offset
     * @throws DecompileException
     */
    public int getJumpObject() throws DecompileException {
        return getCurrentIndex() + getOffset();
    }

    public int getFinalJumpObject() throws DecompileException {
        int address = getJumpObject();
        while (frame.getExecutableAt(address) instanceof UnconditionalJumpInstruction){
            address = ((UnconditionalJumpInstruction)frame.getExecutableAt(address)).getJumpObject();
        }
        return address;
    }
}
