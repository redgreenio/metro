package io.redgreen.metro

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.Opcodes.ASM4

class FieldVisitor : ClassVisitor(ASM4) {
  private val allFields = mutableListOf<Field>()

  val fields: List<Field>
    get() = allFields.toList()

  override fun visitField(
    access: Int,
    name: String,
    desc: String,
    signature: String?,
    value: Any?
  ): FieldVisitor? {
    allFields.add(Field(name, desc.toReadableClassName()))
    return super.visitField(access, name, desc, signature, value)
  }
}
