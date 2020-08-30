package io.redgreen.metro

import jdk.internal.org.objectweb.asm.ClassReader
import java.io.InputStream

class ReadClassFileUseCase {
  companion object {
    private const val FORWARD_SLASH = '/'
    private const val DOT = '.'
  }

  fun invoke(classFileStream: InputStream): Result {
    val classReader = ClassReader(classFileStream)
    return Result(classReader.className.replace(FORWARD_SLASH, DOT))
  }

  data class Result(val className: String)
}
