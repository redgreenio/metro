package io.redgreen.metro

import org.objectweb.asm.ClassReader
import java.io.InputStream

class ReadClassFileUseCase {
  fun invoke(classFileStream: InputStream): Result {
    classFileStream.use { stream ->
      val classReader = ClassReader(stream)
      val classVisitor = MetroClassVisitor()
      classReader.accept(classVisitor, 0)
      return Result(classReader.className.toReadableTypeName(), classVisitor.fields, classVisitor.methods)
    }
  }

  data class Result(
    val className: String,
    val fields: List<Field> = emptyList(),
    val invokables: List<Invokable> = listOf(DefaultConstructor)
  )
}
