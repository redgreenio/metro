package io.redgreen.metro.graph

import com.google.common.truth.Truth.assertThat
import io.redgreen.metro.ReadClassFileUseCase
import io.redgreen.metro.TestClassFile
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ClassGraphTest {
  @ParameterizedTest
  @ValueSource(strings = ["Puppy", "Albatross"])
  fun `it should return no vertices for classes without fields and methods`(className: String) {
    // given
    val classWithoutFieldsAndMethods = TestClassFile(className)

    // when
    val result = ReadClassFileUseCase().invoke(classWithoutFieldsAndMethods.stream())

    // then
    assertThat(result.classGraph.vertexSet())
      .isEmpty()
  }
}
