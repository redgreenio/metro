package io.redgreen.metro

sealed class Invokable

object DefaultConstructor : Invokable() {
  override fun toString(): String {
    return DefaultConstructor::class.java.simpleName
  }
}

data class Method(
  val name: String
) : Invokable()
