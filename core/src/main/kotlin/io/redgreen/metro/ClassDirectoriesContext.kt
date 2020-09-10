package io.redgreen.metro

import io.redgreen.metro.DirectoryPath.Existent
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class ClassDirectoriesContext(private val existent: Existent) : Context {
  override fun getClass(fullyQualifiedClassName: String): InputStream {
    return FileInputStream(toClassFilePath(fullyQualifiedClassName))
  }

  private fun toClassFilePath(fullyQualifiedClassName: String): File {
    val classFileSubPath = fullyQualifiedClassName.replace('.', '/') + ".class"
    return existent.directoryPath.resolve(classFileSubPath).toFile()
  }
}
