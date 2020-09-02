package io.redgreen.metro.graph

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout
import com.mxgraph.util.mxCellRenderer
import io.redgreen.metro.ReadClassFileUseCase.Result
import org.jgraph.graph.DefaultEdge
import org.jgrapht.ext.JGraphXAdapter
import java.awt.Color
import java.awt.image.BufferedImage

object ClassVisualizer {
  fun visualize(result: Result): BufferedImage {
    val graphAdapter = JGraphXAdapter<String, DefaultEdge>(result.classGraph)
    mxHierarchicalLayout(graphAdapter).apply {
      execute(graphAdapter.defaultParent)
    }
    return mxCellRenderer.createBufferedImage(graphAdapter, null, 2.0, Color.WHITE, true, null)
  }
}
