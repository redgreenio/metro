package io.redgreen.metro.graph

import io.redgreen.metro.ReadClassFileUseCase
import io.redgreen.metro.TestClassFile
import io.redgreen.metro.approvals.ImageApprover
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ClassVisualizerTest {
  private val readClassFileUseCase = ReadClassFileUseCase()

  @Test
  fun `it should throw an exception for empty graphs`() {
    // given
    val emptyClass = readClassFileUseCase
      .invoke(TestClassFile("Puppy").stream())

    // when and then
    assertThrows<IllegalStateException> {
      ClassVisualizer.visualize(emptyClass)
    }
  }

  @Test
  fun `it should draw a graph that only has fields`() {
    // given
    val classWithFields = readClassFileUseCase
      .invoke(TestClassFile("Kitten").stream())

    // when
    val graphImage = ClassVisualizer.visualize(classWithFields)

    // then
    Approvals.verify(ImageApprover.create(graphImage))
  }

  @Test
  fun `it should draw a graph that only has methods`() {
    // given
    val classWithMethods = readClassFileUseCase
      .invoke(TestClassFile("Sloth").stream())

    // when
    val graphImage = ClassVisualizer.visualize(classWithMethods)

    // then
    Approvals.verify(ImageApprover.create(graphImage))
  }

  @Test
  fun `it should draw a graph that has fields and methods`() {
    // given
    val classWithFieldsAndMethodsResult = readClassFileUseCase
      .invoke(TestClassFile("Dino").stream())

    // when
    val graphImage = ClassVisualizer.visualize(classWithFieldsAndMethodsResult)

    // then
    Approvals.verify(ImageApprover.create(graphImage))
  }

  @Test
  fun `it should draw a graph that has private method calls`() {
    // given
    val classWithPrivateMethodCallsResult = readClassFileUseCase
      .invoke(TestClassFile("Mario").stream())

    // when
    val graphImage = ClassVisualizer.visualize(classWithPrivateMethodCallsResult)

    // then
    Approvals.verify(ImageApprover.create(graphImage))
  }
}
