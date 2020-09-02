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

      return Result(classReader.className.toReadableTypeName(), classVisitor.fields, classVisitor.methods).apply {
        classVisitor.fields.onEach { field -> classGraph.addVertex(field.name) }
        classVisitor.methods.onEach { invokable -> if (invokable is Method) classGraph.addVertex(invokable.name) }
      }
    }
  }

  data class Result(
    val className: String,
    val fields: List<Field> = emptyList(),
    val invokables: List<Invokable> = listOf(DefaultConstructor)
  ) {
    val classGraph: DefaultDirectedGraph<String, DefaultEdge> = DefaultDirectedGraph(DefaultEdge::class.java)
  }
}
