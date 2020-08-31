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
    private const val CLASS_WITH_FIELDS_AND_METHODS_FILE = "Dino.class"
    private const val CLASS_WITH_MULTIPLE_PARAMETERS_METHOD = "Sloth.class"
  }

  private val readClassFileUseCase = ReadClassFileUseCase()

  @Test
  fun `it should retrieve the name from a class file`() {
    // given
    val emptyClassFileStream = readClassFile(EMPTY_CLASS_FILE)

    // when
    val actual = readClassFileUseCase.invoke(emptyClassFileStream)

    // then
    val expected = Result("io.redgreen.example.Puppy")
    assertThat(actual)
      .isEqualTo(expected)
  }

  @Test
  fun `it should retrieve field names and types from a class file`() {
    // given
    val classWithFieldsStream = readClassFile(CLASS_WITH_FIELDS_FILE)

    // when
    val actual = readClassFileUseCase.invoke(classWithFieldsStream)

    // then
    val fields = listOf(
      Field("name", "java.lang.String"),
      Field("age", "int")
    )
    val expected = Result("io.redgreen.example.Kitten", fields)

    assertThat(actual)
      .isEqualTo(expected)
  }

  @Test
  fun `it should retrieve invokables from a class file`() {
    // given
    val classWithFieldsAndMethodsStream = readClassFile(CLASS_WITH_FIELDS_AND_METHODS_FILE)

    // when
    val actual = readClassFileUseCase.invoke(classWithFieldsAndMethodsStream)

    // then
    val invokables = listOf(
      DefaultConstructor,
      Method("getName"),
      Method("setName", listOf(ParameterType("java.lang.String"))),
      Method("getBreed"),
      Method("setBreed", listOf(ParameterType("java.lang.String")))
    )
    val fields = listOf(
      Field("name", "java.lang.String"),
      Field("breed", "java.lang.String")
    )
    val expected = Result("io.redgreen.example.Dino", fields, invokables)
    assertThat(actual)
      .isEqualTo(expected)
  }

  @Test
  fun `it should retrieve multiple parameter types from method descriptors`() {
    // given
    val classWithMultipleParametersMethod = readClassFile(CLASS_WITH_MULTIPLE_PARAMETERS_METHOD)

    // when
    val actual = readClassFileUseCase.invoke(classWithMultipleParametersMethod)

    // then
    val invokables = listOf(
      DefaultConstructor,
      Method(
        "procrastinate",
        listOf(
          ParameterType("java.lang.String"),
          ParameterType("java.util.Date"),
          ParameterType("int")
        )
      )
    )
    val expected = Result("io.redgreen.example.Sloth", emptyList(), invokables)
    assertThat(actual)
      .isEqualTo(expected)
  }

  private fun readClassFile(classFileName: String): InputStream {
    return File("$CLASS_FILES_PATH/$classFileName")
      .inputStream()
  }
}
