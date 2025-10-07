package com.ilya3point999k.thaumicconcilium.core;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class AsmHelper {

    public static MethodNode getMethodNodeByName(ClassNode classNode, String methodName, String obfMethodName) {
        for (MethodNode node : classNode.methods) {
            if (node.name.equals(methodName) || node.name.equals(obfMethodName)) {
                return node;
            }
        }
        throw new RuntimeException("Unable to find method named " + methodName + " in class " + classNode.name);
    }

    public static MethodNode getMethodNodeByNameNullable(ClassNode classNode, String methodName, String obfMethodName) {
        for (MethodNode node : classNode.methods) {
            if (node.name.equals(methodName) || node.name.equals(obfMethodName)) {
                return node;
            }
        }
        return null;
    }

    public static AbstractInsnNode getFirstMatchingOpcode(MethodNode node, int opcode) {
        for (int i = 0; i < node.instructions.size(); i++) {
            AbstractInsnNode insnNode = node.instructions.get(i);
            if(insnNode.getOpcode() == opcode)
                return insnNode;
        }
        throw new RuntimeException("Unable to find opcode in " + node);
    }

    public static MethodNode getMethodNodeByDescriptor(ClassNode classNode, String methodName, String obfMethodName, String descriptor) {
        for (MethodNode node : classNode.methods) {
            if ((node.name.equals(methodName) || node.name.equals(obfMethodName)) && node.desc.equals(descriptor)) {
                return node;
            }
        }
        throw new RuntimeException("Unable to find method named " + methodName + " in class " + classNode.name);
    }

    public static MethodInsnNode getFirstMatchingMethodInsNode(MethodNode methodNode, String owner, String methodName, String obfName, String descriptor) {
        for (int i = 0; i < methodNode.instructions.size(); i++) {
            AbstractInsnNode insnNode = methodNode.instructions.get(i);
            if(!(insnNode instanceof MethodInsnNode))
                continue;
            MethodInsnNode min = (MethodInsnNode) insnNode;
            if(min.owner.equals(owner) && min.desc.equals(descriptor) && (min.name.equals(methodName) || min.name.equals(obfName))) {
                return min;
            }
        }
        throw new RuntimeException("Unable to find method instruction in " + methodNode);
    }
}
