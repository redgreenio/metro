package io.redgreen.metro

import io.redgreen.metro.annotations.FilePrivate
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

sealed class DirectoryPath {
  companion object {
    fun from(possibleDirectoryPath: String): DirectoryPath {
      val file = File(possibleDirectoryPath)
      return if (file.exists() && file.isDirectory) {
        Existent(Paths.get(file.canonicalPath))
      } else if (file.isFile) {
        NotDirectory(Paths.get(file.canonicalPath))
      } else {
        NonExistent(possibleDirectoryPath)
      }
    }
  }

  data class Existent @FilePrivate constructor(
    val directoryPath: Path
  ) : DirectoryPath()

  data class NonExistent @FilePrivate constructor(
    val path: String
  ) : DirectoryPath()

  data class NotDirectory @FilePrivate constructor(
    val filePath: Path
  ) : DirectoryPath()
}
