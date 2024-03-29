package io.redgreen.metro

import org.jgraph.graph.DefaultEdge
import org.jgrapht.graph.DefaultDirectedGraph
import org.objectweb.asm.ClassReader
import java.io.InputStream

class ReadClassFileUseCase {
  fun invoke(classFileStream: InputStream): Result {
    classFileStream.use { stream ->
      val classReader = ClassReader(stream)
      val classVisitor = MetroClassVisitor()
      classReader.accept(classVisitor, 0)

      val className = classReader.className.toReadableTypeName()
      return Result(className, classVisitor.fields, classVisitor.methods)
        .apply { classGraph = classVisitor.mutableClassGraph }
    }
  }

  data class Result(
    val className: String,
    val fields: List<Field> = emptyList(),
    val invokables: List<Invokable> = listOf(DefaultConstructor)
  ) {
    lateinit var classGraph: DefaultDirectedGraph<String, DefaultEdge>
  }
}
