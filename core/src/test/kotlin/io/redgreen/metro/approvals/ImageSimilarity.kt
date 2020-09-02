package io.redgreen.metro.approvals

import java.awt.image.BufferedImage
import kotlin.math.abs

/**
 * Method was copied from [RosettaCode](https://rosettacode.org/wiki/Percentage_difference_between_images#Kotlin)
 **/
object ImageSimilarity {

  fun differenceBetween(image1: BufferedImage, image2: BufferedImage): Double {
    val width = image1.width
    val height = image1.height
    val width2 = image2.width
    val height2 = image2.height
    if (width != width2 || height != height2) {
      val f = "(%d,%d) vs. (%d,%d)".format(width, height, width2, height2)
      throw IllegalArgumentException("Images must have the same dimensions: $f")
    }
    var diff = 0L
    for (y in 0 until height) {
      for (x in 0 until width) {
        diff += pixelDiff(image1.getRGB(x, y), image2.getRGB(x, y))
      }
    }
    val maxDiff = 3L * 255 * width * height
    return diff / maxDiff.toDouble()
  }

  fun similarity(img1: BufferedImage, img2: BufferedImage): Double {
    return 1.0 - differenceBetween(img1, img2)
  }

  private fun pixelDiff(rgb1: Int, rgb2: Int): Int {
    val r1 = (rgb1 shr 16) and 0xff
    val g1 = (rgb1 shr 8) and 0xff
    val b1 = rgb1 and 0xff
    val r2 = (rgb2 shr 16) and 0xff
    val g2 = (rgb2 shr 8) and 0xff
    val b2 = rgb2 and 0xff
    return abs(r1 - r2) + abs(g1 - g2) + abs(b1 - b2)
  }
}
