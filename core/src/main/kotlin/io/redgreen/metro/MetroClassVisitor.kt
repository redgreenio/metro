package io.redgreen.metro

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASM4

class MetroClassVisitor : ClassVisitor(ASM4) {
  private val allFields = mutableListOf<Field>()
  private val invokables = mutableListOf<Invokable>()

  val fields: List<Field>
    get() = allFields.toList()

  val methods: List<Invokable>
    get() = invokables.toList()

  override fun visitField(
    access: Int,
    name: String,
    descriptor: String,
    signature: String?,
    value: Any?
  ): FieldVisitor? {
    allFields.add(Field(name, descriptor.toReadableTypeName()))
    return super.visitField(access, name, descriptor, signature, value)
  }

  override fun visitMethod(
    access: Int,
    name: String,
    descriptor: String,
    signature: String?,
    exceptions: Array<out String>?
  ): MethodVisitor? {
    val invokable = if (name == "<init>") {
      DefaultConstructor
    } else {
      val parameterTypeNames = descriptor.getParameterTypeNames()
      Method(name, parameterTypeNames.map(::ParameterType))
    }
    invokables.add(invokable)
    return super.visitMethod(access, name, descriptor, signature, exceptions)
  }
}
