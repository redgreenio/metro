package io.redgreen.metro

private const val FORWARD_SLASH = '/'
private const val DOT = '.'
private const val RIGHT_PAREN = ')'
private const val SEMI_COLON = ';'
private const val L = 'L'
private const val CLASS_FILE_INT = "I"
private const val JAVA_INT = "int"
private const val CLASS_FILE_DOUBLE = "D"
private const val JAVA_DOUBLE = "double"
private const val CLASS_FILE_VOID = "V"
private const val JAVA_VOID = "void"

internal fun String.toReadableTypeName(): String = when {
  this == CLASS_FILE_INT -> JAVA_INT
  this == CLASS_FILE_DOUBLE -> JAVA_DOUBLE
  this == CLASS_FILE_VOID -> JAVA_VOID
  startsWith(L) && endsWith(SEMI_COLON) -> replace(FORWARD_SLASH, DOT).substring(1, this.length - 1)
  contains(FORWARD_SLASH) -> replace(FORWARD_SLASH, DOT)
  else -> throw UnsupportedOperationException("Unknown class name: $this")
}

internal fun String.getParameterTypeNames(): List<String> = when {
  startsWith("()") -> emptyList()

  split(';').size >= 2 -> extractParameterList()
    .split(SEMI_COLON)
    .map { if (it.startsWith(L)) it.substring(1) else it }
    .filter { it.isNotBlank() }
    .map(String::toReadableTypeName)

  else -> listOf(extractParameterList().toReadableTypeName())
}

private fun String.extractParameterList(): String =
  substring(1, indexOf(RIGHT_PAREN))
