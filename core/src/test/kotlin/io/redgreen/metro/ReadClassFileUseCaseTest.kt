package io.redgreen.metro

import com.google.common.truth.Truth.assertThat
import io.redgreen.metro.ReadClassFileUseCase.Result
import org.junit.jupiter.api.Test
import java.io.InputStream

class ReadClassFileUseCaseTest {
  companion object {
    private const val EMPTY_CLASS_FILE = "/classes/Puppy.class"
    private const val CLASS_WITH_FIELDS_FILE = "/classes/Kitten.class"
  }

  private val readClassFileUseCase = ReadClassFileUseCase()

  @Test
  fun `it should retrieve the name from a class file`() {
    // given
    val emptyClassFileStream = readClassFile(EMPTY_CLASS_FILE)

    // when
    val result = readClassFileUseCase.invoke(emptyClassFileStream)

    // then
    assertThat(result.className)
      .isEqualTo("io.redgreen.example.Puppy")
  }

  @Test
  fun `it should retrieve field names and types from a class file`() {
    // given
    val classWithFieldsStream = readClassFile(CLASS_WITH_FIELDS_FILE)

    // when
    val actualResult = readClassFileUseCase.invoke(classWithFieldsStream)

    // then
    val fields = listOf(
      Field("name", "java.lang.String"),
      Field("age", "int")
    )
    val expectedResult = Result("io.redgreen.example.Kitten", fields)

    assertThat(actualResult)
      .isEqualTo(expectedResult)
  }

  private fun readClassFile(resourceFile: String): InputStream {
    return ReadClassFileUseCaseTest::class.java
      .getResourceAsStream(resourceFile)
  }
}
