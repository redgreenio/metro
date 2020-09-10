package io.redgreen.metro

import com.google.common.truth.Truth.assertThat
import io.redgreen.metro.DirectoryPath.Existent
import org.junit.jupiter.api.Test
import java.io.File

class ContextTest {
  companion object {
    private const val CLASS_FILES_DIRECTORY_PATH = "../core-test-fixtures/build/classes/java/main"
  }

  private val existent = DirectoryPath.from(CLASS_FILES_DIRECTORY_PATH) as Existent
  private val context = Context.from(existent)

  @Test
  fun `it should load a specified class from a package`() {
    // when
    val actualBytes = context.getClass("io.redgreen.example.Albatross").use {
      it.readBytes()
    }

    // then
    val albatrossBytes = TestClassFile("Albatross").stream().use { it.readBytes() }
    assertThat(actualBytes)
      .isEqualTo(albatrossBytes)
  }

  @Test
  fun `it should load a class without a package`() {
    // when
    val className = "TopLevelClass"
    val actualBytes = context.getClass(className).use { it.readBytes() }

    // then
    val topLevelClassBytes = File(CLASS_FILES_DIRECTORY_PATH).resolve("$className.class")
      .inputStream()
      .use { it.readBytes() }
    assertThat(actualBytes)
      .isEqualTo(topLevelClassBytes)
  }
}
