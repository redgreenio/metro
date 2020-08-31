package io.redgreen.metro

import java.io.File
import java.io.InputStream

/**
 * This class uses compiled classes from the 'core-test-fixtures' module for testing. It targets the module's
 * build directory and uses class names to access compiled class files.
 */
class TestClassFile(
  private val name: String
) {
  companion object {
    private const val CLASS_FILES_PATH = "./../core-test-fixtures/build/classes/java/main/io/redgreen/example"
  }

  fun stream(): InputStream {
    return File("$CLASS_FILES_PATH/$name.class")
      .inputStream()
  }
}
