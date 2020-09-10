package io.redgreen.metro

import com.google.common.truth.Truth.assertThat
import io.redgreen.metro.DirectoryPath.Existent
import io.redgreen.metro.DirectoryPath.NonExistent
import io.redgreen.metro.DirectoryPath.NotDirectory
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class DirectoryPathTest {
  @Test
  fun `it should return an existent object for an existing directory`() {
    // given
    val possibleDirectoryPath = "../core-test-fixtures"

    // when
    val actual = DirectoryPath.from(possibleDirectoryPath)

    // then
    assertThat(actual)
      .isEqualTo(Existent(possibleDirectoryPath.toPath()))
  }

  @Test
  fun `it should return a non-existent object for an non-existent directory`() {
    // given
    val path = "i-dont-exist"

    // when
    val actual = DirectoryPath.from(path)

    // then
    assertThat(actual)
      .isEqualTo(NonExistent(path))
  }

  @Test
  fun `it should return not a directory for an existent path which is a file`() {
    // given
    val filePath = "../build.gradle"

    // when
    val actual = DirectoryPath.from(filePath)

    // then
    assertThat(actual)
      .isEqualTo(NotDirectory(filePath.toPath()))
  }

  private fun String.toPath(): Path =
    Paths.get(File(this).canonicalPath)
}
