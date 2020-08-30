package io.redgreen.metro

private const val FORWARD_SLASH = '/'
private const val DOT = '.'

internal fun String.toReadableClassName(): String = when {
  this == "I" -> "int"
  startsWith("L") && endsWith(";") -> replace(FORWARD_SLASH, DOT).substring(1, this.length - 1)
  contains(FORWARD_SLASH) -> replace(FORWARD_SLASH, DOT)
  else -> throw UnsupportedOperationException("Unknown class name: $this")
}
