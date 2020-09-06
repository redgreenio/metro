package io.redgreen.metro.demo

import io.redgreen.metro.ReadClassFileUseCase
import io.redgreen.metro.graph.ClassVisualizer
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO

/*
 * This is a quick way to visualize compiled classes in the project. I use it during development to verify my
 * assumptions around the graph logic. This could either move to a separate module or be deleted forever after
 * I gain a good understanding of the domain.
 */
fun main() {
  val className = "Kangaroo$1"
  val graphImage = getClassFileStream(className).use {
    ClassVisualizer.visualize(ReadClassFileUseCase().invoke(it))
  }
  val imageFileOutputPath = File("./build/$className.png")
  ImageIO.write(graphImage, "png", imageFileOutputPath)
  println("Written graph image to: ${imageFileOutputPath.path}")
}

private fun getClassFileStream(
  @Suppress("SameParameterValue") className: String
): InputStream {
  return File("./core-test-fixtures/build/classes/java/main/io/redgreen/example/$className.class")
    .inputStream()
}
