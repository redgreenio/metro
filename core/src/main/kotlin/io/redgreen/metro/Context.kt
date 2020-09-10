package io.redgreen.metro

import io.redgreen.metro.DirectoryPath.Existent
import java.io.InputStream

interface Context {
  companion object {
    fun from(existent: Existent): Context =
      ClassDirectoriesContext(existent)
  }

  fun getClass(fullyQualifiedClassName: String): InputStream
}
