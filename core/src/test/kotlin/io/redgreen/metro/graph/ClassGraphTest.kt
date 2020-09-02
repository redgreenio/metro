package io.redgreen.metro.graph

import com.google.common.truth.Truth.assertThat
import io.redgreen.metro.ReadClassFileUseCase
import io.redgreen.metro.TestClassFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ClassGraphTest {
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
}
