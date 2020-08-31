package io.redgreen.metro

import com.google.common.truth.Truth.assertThat
import io.redgreen.metro.ReadClassFileUseCase.Result
import org.junit.jupiter.api.Test

class ReadClassFileUseCaseTest {
  companion object {
    private val EMPTY_CLASS = TestClassFile("Puppy")
    private val ONLY_FIELDS_CLASS = TestClassFile("Kitten")
    private val FIELDS_AND_METHODS_CLASS = TestClassFile("Dino")
    private val METHOD_MULTIPLE_PARAMETERS_CLASS = TestClassFile("Sloth")
    private val CONSTRUCTOR_WITH_MULTIPLE_PARAMETERS = TestClassFile("Albatross")
  }

  private val readClassFileUseCase = ReadClassFileUseCase()

  @Test
  fun `it should retrieve the name from a class file`() {
    // when
    val actual = readClassFileUseCase.invoke(EMPTY_CLASS.stream())

    // then
    val expected = Result("io.redgreen.example.Puppy")
    assertThat(actual)
      .isEqualTo(expected)
  }

  @Test
  fun `it should retrieve field names and types from a class file`() {
    // when
    val actual = readClassFileUseCase.invoke(ONLY_FIELDS_CLASS.stream())

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
    // when
    val actual = readClassFileUseCase.invoke(FIELDS_AND_METHODS_CLASS.stream())

    // then
    val invokables = listOf(
      DefaultConstructor,
      Method("getName", returnType = Type("java.lang.String")),
      Method("setName", listOf(Type("java.lang.String"))),
      Method("getBreed", returnType = Type("java.lang.String")),
      Method("setBreed", listOf(Type("java.lang.String")))
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
    // when
    val actual = readClassFileUseCase.invoke(METHOD_MULTIPLE_PARAMETERS_CLASS.stream())

    // then
    val invokables = listOf(
      DefaultConstructor,
      Method(
        "procrastinate",
        listOf(
          Type("java.lang.String"),
          Type("java.util.Date"),
          Type("int")
        )
      )
    )
    val expected = Result("io.redgreen.example.Sloth", emptyList(), invokables)
    assertThat(actual)
      .isEqualTo(expected)
  }

  @Test
  fun `it should parse constructors with multiple parameters`() {
    // when
    val actual = readClassFileUseCase.invoke(CONSTRUCTOR_WITH_MULTIPLE_PARAMETERS.stream())

    // then
    val invokables = listOf(
      Constructor(
        listOf(
          Type("java.lang.String"),
          Type("double")
        )
      )
    )
    val expected = Result("io.redgreen.example.Albatross", emptyList(), invokables)

    assertThat(actual)
      .isEqualTo(expected)
  }
}
