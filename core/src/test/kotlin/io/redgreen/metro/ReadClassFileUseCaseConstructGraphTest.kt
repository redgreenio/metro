package io.redgreen.metro

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ReadClassFileUseCaseConstructGraphTest {
  private val readClassFileUseCase = ReadClassFileUseCase()

  @ParameterizedTest
  @ValueSource(strings = ["Puppy", "Albatross"])
  fun `it should return no vertices for classes without fields and methods`(className: String) {
    // given
    val classWithoutFieldsAndMethods = TestClassFile(className)

    // when
    val result = readClassFileUseCase.invoke(classWithoutFieldsAndMethods.stream())

    // then
    assertThat(result.classGraph.vertexSet())
      .isEmpty()
  }

  @Test
  fun `it should return field vertices that are not connected`() {
    // given
    val classWithFields = TestClassFile("Kitten")

    // when
    val result = readClassFileUseCase.invoke(classWithFields.stream())

    // then
    assertThat(result.classGraph.vertexSet())
      .containsExactly("name", "age")
    assertThat(result.classGraph.edgeSet())
      .isEmpty()
  }

  @Test
  fun `it should return method vertices that are not connected`() {
    // given
    val classWithOneMethod = TestClassFile("Sloth")

    // when
    val result = readClassFileUseCase.invoke(classWithOneMethod.stream())

    // then
    assertThat(result.classGraph.vertexSet())
      .containsExactly("procrastinate")
    assertThat(result.classGraph.edgeSet())
      .isEmpty()
  }

  @Test
  fun `it should return a graph with connected edges`() {
    // given
    val classWithFieldsAndAccessorMethods = TestClassFile("Dino")

    // when
    val result = readClassFileUseCase.invoke(classWithFieldsAndAccessorMethods.stream())

    // then
    with(result) {
      assertThat(classGraph.vertexSet())
        .containsExactly("name", "getName", "setName", "breed", "getBreed", "setBreed")
      assertThat(classGraph.edgeSet())
        .hasSize(4)
    }
  }
}
