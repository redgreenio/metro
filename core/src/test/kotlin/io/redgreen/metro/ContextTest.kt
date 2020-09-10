package io.redgreen.metro

import com.google.common.truth.Truth.assertThat
import io.redgreen.metro.DirectoryPath.Existent
import org.junit.jupiter.api.Test

class ContextTest {
  companion object {
    private const val CLASS_FILES_DIRECTORY_PATH = "../core-test-fixtures/build/classes/java/main"
  }

  @Test
  fun `it should load a specified class from a package`() {
    // given
    val existent = DirectoryPath.from(CLASS_FILES_DIRECTORY_PATH) as Existent
    val context = Context.from(existent)

    // when
    val actualBytes = context.getClass("io.redgreen.example.Albatross").use {
      it.readBytes()
    }

    // then
    val albatrossBytes = TestClassFile("Albatross").stream().use { it.readBytes() }
    assertThat(actualBytes)
      .isEqualTo(albatrossBytes)
  }
}
