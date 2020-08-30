package io.redgreen.metro

import com.google.common.truth.Truth.assertThat
import io.redgreen.metro.ReadClassFileUseCase.Result
import org.junit.jupiter.api.Test
import java.io.File
import java.io.InputStream

class ReadClassFileUseCaseTest {
  companion object {
    private const val CLASS_FILES_PATH = "./../core-test-fixtures/build/classes/java/main/io/redgreen/example"
    private const val EMPTY_CLASS_FILE = "Puppy.class"
    private const val CLASS_WITH_FIELDS_FILE = "Kitten.class"
  }

  private val readClassFileUseCase = ReadClassFileUseCase()

  @Test
  fun `it should retrieve the name from a class file`() {
    // given
    val emptyClassFileStream = readClassFile(EMPTY_CLASS_FILE)

    // when
    val result = readClassFileUseCase.invoke(emptyClassFileStream)

    // then
    assertThat(result)
      .isEqualTo(Result("io.redgreen.example.Puppy", emptyList()))
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

  private fun readClassFile(classFileName: String): InputStream {
    return File("$CLASS_FILES_PATH/$classFileName")
      .inputStream()
  }
}
