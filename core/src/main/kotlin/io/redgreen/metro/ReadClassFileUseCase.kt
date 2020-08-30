package io.redgreen.metro

import org.objectweb.asm.ClassReader
import java.io.InputStream

class ReadClassFileUseCase {
  fun invoke(classFileStream: InputStream): Result {
    val classReader = ClassReader(classFileStream)
    val fieldVisitor = FieldVisitor()
    classReader.accept(fieldVisitor, 0)
    return Result(classReader.className.toReadableClassName(), fieldVisitor.fields)
  }

  data class Result(val className: String, val fields: List<Field>)
}