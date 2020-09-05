package io.redgreen.metro

import org.jgraph.graph.DefaultEdge
import org.jgrapht.graph.DefaultDirectedGraph
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASM8

internal class MetroClassVisitor : ClassVisitor(ASM8) {
  companion object {
    private const val OPCODE_GETFIELD = 0xB4
    private const val OPCODE_PUTFIELD = 0xB5
    private const val OPCODE_INVOKESPECIAL = 0xB7

    private const val OPCODE_CONSTRUCTOR = "<init>"
  }

  private val allFields = mutableListOf<Field>()
  private val invokables = mutableListOf<Invokable>()

  val fields: List<Field>
    get() = allFields.toList()

  val methods: List<Invokable>
    get() = invokables.toList()

  val mutableClassGraph: DefaultDirectedGraph<String, DefaultEdge> =
    DefaultDirectedGraph(DefaultEdge::class.java)

  override fun visitField(
    access: Int,
    name: String,
    descriptor: String,
    signature: String?,
    value: Any?
  ): FieldVisitor? {
    allFields.add(Field(name, descriptor.toReadableTypeName()))
    mutableClassGraph.addVertex(name)
    return super.visitField(access, name, descriptor, signature, value)
  }

  override fun visitMethod(
    access: Int,
    name: String,
    descriptor: String,
    signature: String?,
    exceptions: Array<out String>?
  ): MethodVisitor? {
    val invokable = if (name == "<init>" && descriptor == "()V") {
      DefaultConstructor
    } else {
      val parameterTypes = descriptor
        .getParameterTypeNames()
        .map(::Type)

      if (name == "<init>") {
        Constructor(parameterTypes)
      } else {
        val returnTypeName = descriptor
          .substring(descriptor.lastIndexOf(')') + 1)
          .toReadableTypeName()
        mutableClassGraph.addVertex(name)

        Method(name, parameterTypes, Type(returnTypeName))
      }
    }
    invokables.add(invokable)
    return MetroMethodVisitor(name)
  }

  inner class MetroMethodVisitor(private val methodName: String) : MethodVisitor(ASM8) {
    override fun visitMethodInsn(
      opcode: Int,
      owner: String?,
      name: String?,
      descriptor: String?,
      isInterface: Boolean
    ) {
      if (opcode == OPCODE_INVOKESPECIAL && name != OPCODE_CONSTRUCTOR) {
        if (!mutableClassGraph.vertexSet().contains(name)) {
          mutableClassGraph.addVertex(name)
        }
        mutableClassGraph.addEdge(methodName, name)
      }
      super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }

    override fun visitFieldInsn(
      opcode: Int,
      owner: String?,
      fieldName: String?,
      descriptor: String?
    ) {
      when (opcode) {
        OPCODE_GETFIELD, OPCODE_PUTFIELD -> mutableClassGraph.addEdge(methodName, fieldName)
      }
      super.visitFieldInsn(opcode, owner, fieldName, descriptor)
    }
  }
}
