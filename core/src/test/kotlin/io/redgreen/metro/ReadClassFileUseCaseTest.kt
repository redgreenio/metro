package io.redgreen.metro

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.io.InputStream

class ReadClassFileUseCaseTest {
  companion object {
    private const val EMPTY_CLASS_FILE = "/classes/Puppy.class"
  }

  @Test
  fun `it should retrieve the name from a class file`() {
    // given
    val emptyClassFileStream = readClassFile(EMPTY_CLASS_FILE)

    // when
    val result = ReadClassFileUseCase().invoke(emptyClassFileStream)

    // then
    assertThat(result.className)
      .isEqualTo("io.redgreen.example.Puppy")
  }

  private fun readClassFile(resourceFile: String): InputStream {
    return ReadClassFileUseCaseTest::class.java
      .getResourceAsStream(resourceFile)
  }
}
