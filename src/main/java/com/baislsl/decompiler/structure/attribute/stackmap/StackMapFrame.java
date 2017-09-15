package com.baislsl.decompiler.structure.attribute.stackmap;

import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.structure.Name;
import com.baislsl.decompiler.structure.attribute.Builder;
import com.baislsl.decompiler.structure.constantPool.ConstantPool;
import com.baislsl.decompiler.utils.Read;

import java.io.DataInputStream;

public abstract class StackMapFrame implements Builder, Name {
    private static final int FRAME_TYPE_SIZE = 1;
    static final int OFFSET_DELTA_SIZE = 2;
    protected int frameType;
    protected ConstantPool[] constantPools;

    StackMapFrame(int frameType, ConstantPool[] constantPools) {
        this.frameType = frameType;
        this.constantPools = constantPools;
    }


    public static StackMapFrame getStackFrame(DataInputStream file, ConstantPool[] constantPools)
            throws DecompileException {
        int frameType = Read.readBytes(file, FRAME_TYPE_SIZE);
        StackMapFrame ans;
        if (frameType < 64) {
            ans = new SameFrame(frameType, constantPools);
        } else if (frameType < 128) {
            ans = new SameLocals1StackItemFrame(frameType, constantPools);
        } else if (frameType == 247) {
            ans = new SameLocals1StackItemFrameExtend(frameType, constantPools);
        } else if (frameType >= 248 && frameType <= 250) {
            ans = new ChopFrame(frameType, constantPools);
        } else if (frameType == 251) {
            ans = new SameFrameExtend(frameType, constantPools);
        } else if (frameType >= 252 && frameType <= 254) {
            ans = new AppendFrame(frameType, constantPools);
        } else if (frameType == 255) {
            ans = new FullFrame(frameType, constantPools);
        } else {
            throw new DecompileException(
                    String.format("Error frame type of %d when building stack map frame", frameType)
            );
        }
        ans.build(file);
        return ans;
    }

    public int getFrameType() {
        return frameType;
    }

    @Override
    public String name() throws DecompileException {
        StringBuilder ans = new StringBuilder();
        ans.append("frame_type=")
                .append(frameType)
                .append("  /* ")
                .append(getClass().getSimpleName())
                .append(" */");
        return ans.toString();
    }
}


class SameFrame extends StackMapFrame {

    SameFrame(int frameType, ConstantPool[] constantPools) {
        super(frameType, constantPools);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {

    }
}

class SameLocals1StackItemFrame extends StackMapFrame {
    protected VerificationTypeInfo stack;

    SameLocals1StackItemFrame(int frameType, ConstantPool[] constantPools) {
        super(frameType, constantPools);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        stack = VerificationTypeInfo.getVerificationTypeInfo(file, constantPools);
    }

    public VerificationTypeInfo getStack() {
        return stack;
    }

    @Override
    public String name() throws DecompileException {
        return super.name() + "\nstack=" + stack.name();
    }
}

class SameLocals1StackItemFrameExtend extends SameLocals1StackItemFrame {
    private int offsetDelta;

    SameLocals1StackItemFrameExtend(int frameType, ConstantPool[] constantPools) {
        super(frameType, constantPools);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
        stack = VerificationTypeInfo.getVerificationTypeInfo(file, constantPools);
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }

    @Override
    public String name() throws DecompileException {
        return super.name() + "\noffsetDelta=" + offsetDelta;
    }
}

class ChopFrame extends StackMapFrame {
    private int offsetDelta;

    ChopFrame(int frameType, ConstantPool[] constantPools) {
        super(frameType, constantPools);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }

    @Override
    public String name() throws DecompileException {
        return super.name() + "\noffset_delta=" + offsetDelta;
    }
}


class SameFrameExtend extends StackMapFrame {
    private int offsetDelta;

    SameFrameExtend(int frameType, ConstantPool[] constantPools) {
        super(frameType, constantPools);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }

    @Override
    public String name() throws DecompileException {
        return super.name() + "\noffset_delta=" + offsetDelta;
    }
}

class AppendFrame extends StackMapFrame {
    private int offsetDelta;
    private VerificationTypeInfo[] locals;
    private ConstantPool[] constantPools;

    AppendFrame(int frameType, ConstantPool[] constantPools) {
        super(frameType, constantPools);

    }

    public void build(DataInputStream file)
            throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
        locals = new VerificationTypeInfo[frameType - 251];
        for (int i = 0; i < locals.length; i++) {
            locals[i] = VerificationTypeInfo.getVerificationTypeInfo(file, constantPools);
        }
    }

    public int getOffsetDelta() {
        return offsetDelta;
    }

    public VerificationTypeInfo[] getLocals() {
        return locals;
    }

    @Override
    public String name() throws DecompileException {
        StringBuilder ans = new StringBuilder(super.name());
        ans.append("\n")
                .append("offset_delta=").append(offsetDelta)
                .append("\n")
                .append("locals=[");
        for (int i = 0; i < locals.length; i++) {
            if (i != 0) {
                ans.append(", ");
            }
            ans.append(locals[i].name());
        }
        ans.append("]");

        return ans.toString();
    }
}


class FullFrame extends StackMapFrame {
    private static final int NUMBER_SIZE = 2;
    private int offsetDelta;
    private VerificationTypeInfo[] locals;
    private VerificationTypeInfo[] stacks;

    FullFrame(int frameType, ConstantPool[] constantPools) {
        super(frameType, constantPools);
    }

    @Override
    public void build(DataInputStream file) throws DecompileException {
        offsetDelta = Read.readBytes(file, OFFSET_DELTA_SIZE);
        int localsNumber = Read.readBytes(file, NUMBER_SIZE);
        locals = new VerificationTypeInfo[localsNumber];
        for (int i = 0; i < localsNumber; i++) {
            locals[i] = VerificationTypeInfo.getVerificationTypeInfo(file, constantPools);
        }

        int stacksNumber = Read.readBytes(file, NUMBER_SIZE);
        stacks = new VerificationTypeInfo[stacksNumber];
        for (int i = 0; i < stacksNumber; i++) {
            stacks[i] = VerificationTypeInfo.getVerificationTypeInfo(file, constantPools);
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

    @Override
    public String name() throws DecompileException {
        StringBuilder ans = new StringBuilder(super.name());
        ans.append("\n")
                .append("offset_delta=").append(offsetDelta)
                .append("\n")
                .append("locals=[");
        for (int i = 0; i < locals.length; i++) {
            if (i != 0) {
                ans.append(", ");
            }
            ans.append(locals[i].name());
        }
        ans.append("]").append("\n");

        ans.append("stacks=[");
        for (int i = 0; i < stacks.length; i++) {
            if (i != 0) {
                ans.append(", ");
            }
            ans.append(stacks[i].name());
        }
        ans.append("]");

        return ans.toString();
    }
}




