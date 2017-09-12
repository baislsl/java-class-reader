package com.baislsl.decompiler.structure.attribute.stackmap;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public abstract class StackMapFrame implements Builder {
    private static final int FRAME_TYPE_SIZE = 1;
    static final int OFFSET_DELTA_SIZE = 2;
    int frameType;

    StackMapFrame(int frameType) {
        this.frameType = frameType;
    }


    static StackMapFrame getStackFrame(DataInputStream file) throws DecompileException {
        int frameType = Read.readBytes(file, FRAME_TYPE_SIZE);
        StackMapFrame ans;
        if (frameType < 64) {
            ans = new SameFrame(frameType);
        } else if (frameType < 128) {
            ans = new SameLocals1StackItemFrame(frameType);
        } else if (frameType == 247) {
            ans = new SameLocals1StackItemFrameExtend(frameType);
        } else if (frameType >= 248 && frameType <= 250) {
            ans = new ChopFrame(frameType);
        } else if (frameType == 251) {
            ans = new SameFrameExtend(frameType);
        } else if (frameType >= 252 && frameType <= 254) {
            ans = new AppendFrame(frameType);
        } else if (frameType == 255) {
            ans = new FullFrame(frameType);
        } else {
            throw new DecompileException(
                    String.format("Erro frame type of %d when building stack map frame", frameType)
            );
        }
        ans.build(file);
        return ans;
    }

    public int getFrameType() {
        return frameType;
    }
}


class SameFrame extends StackMapFrame {

    SameFrame(int frameType) {
        super(frameType);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {

    }
}

class SameLocals1StackItemFrame extends StackMapFrame {
    private VerificationTypeInfo stack;

    SameLocals1StackItemFrame(int frameType) {
        super(frameType);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        stack = VerificationTypeInfo.getVerificationTypeInfo(file);
    }

    public VerificationTypeInfo getStack() {
        return stack;
    }
}

class SameLocals1StackItemFrameExtend extends StackMapFrame {
    private int offsetDelta;
    private VerificationTypeInfo stack;


    SameLocals1StackItemFrameExtend(int frameType) {
        super(frameType);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
        stack = VerificationTypeInfo.getVerificationTypeInfo(file);
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }

    public VerificationTypeInfo getStack() {
        return stack;
    }
}

class ChopFrame extends StackMapFrame {
    private int offsetDelta;

    ChopFrame(int frameType) {
        super(frameType);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }
}


class SameFrameExtend extends StackMapFrame {
    private int offsetDelta;

    SameFrameExtend(int frameType) {
        super(frameType);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }
}

class AppendFrame extends StackMapFrame {
    private int offsetDelta;
    private VerificationTypeInfo[] locals;

    AppendFrame(int frameType) {
        super(frameType);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
        locals = new VerificationTypeInfo[frameType - 251];
        for (int i = 0; i < locals.length; i++) {
            locals[i] = VerificationTypeInfo.getVerificationTypeInfo(file);
        }
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }

    public VerificationTypeInfo[] getLocals() {
        return locals;
    }
}


class FullFrame extends StackMapFrame {
    private static final int NUMBER_SIZE = 2;
    private int offsetDelta;
    private VerificationTypeInfo[] locals;
    private VerificationTypeInfo[] stacks;

    FullFrame(int frameType) {
        super(frameType);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
        int localsNumber = Read.readBytes(file, NUMBER_SIZE);
        locals = new VerificationTypeInfo[localsNumber];
        for (int i = 0; i < localsNumber; i++) {
            locals[i] = VerificationTypeInfo.getVerificationTypeInfo(file);
        }

        int stacksNumber = Read.readBytes(file, NUMBER_SIZE);
        stacks = new VerificationTypeInfo[stacksNumber];
        for (int i = 0; i < stacksNumber; i++) {
            stacks[i] = VerificationTypeInfo.getVerificationTypeInfo(file);
        }
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }

    public VerificationTypeInfo[] getLocals() {
        return locals;
    }

    public VerificationTypeInfo[] getStacks() {
        return stacks;
    }
}




