package com.ilya3point999k.thaumicconcilium.core;

import cpw.mods.fml.relauncher.Side;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public abstract class SubTransformer {

    public final String className;

    public final int flag;

    public SubTransformer(String className) {
        this(className, ClassWriter.COMPUTE_MAXS);
    }

    //If COMPUTE_FRAMES is ever needed
    public SubTransformer(String className, int flag) {
        this.className = className;
        this.flag = flag;
    }

    public abstract boolean shouldApplyOnSide(Side side);

    public abstract void transformClass(ClassNode node);
}
