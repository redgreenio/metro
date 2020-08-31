package io.redgreen.metro

data class Type(
  val name: String
) {
  companion object {
    val VOID = Type("void")
  }
}
