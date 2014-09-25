package org.tjc.scala.swingthings.event

import scala.swing.event.ComponentEvent
import org.tjc.scala.swingthings.Tree
import javax.swing.tree.TreePath

abstract class TreeEvent(override val source: Tree) extends ComponentEvent
abstract class TreeChange(override val source: Tree) extends TreeEvent(source)
abstract class TreeModelChange(override val source: Tree, childIndices: Array[Int], children: Array[Object], path: Array[Object], treePath: TreePath) extends TreeEvent(source)

case class TreeChanged(override val source: Tree) extends TreeChange(source)

case class TreeNodesChanged(override val source: Tree,
                            childIndices: Array[Int],
                            children: Array[Object],
                            path: Array[Object],
                            treePath: TreePath)
  extends TreeModelChange(source, childIndices, children, path, treePath)

case class TreeNodesInserted(override val source: Tree,
                             childIndices: Array[Int],
                             children: Array[Object],
                             path: Array[Object],
                             treePath: TreePath)
  extends TreeModelChange(source, childIndices, children, path, treePath)

case class TreeNodesRemoved(override val source: Tree,
                            childIndices: Array[Int],
                            children: Array[Object],
                            path: Array[Object],
                            treePath: TreePath)
  extends TreeModelChange(source, childIndices, children, path, treePath)

case class TreeStructureChanged(override val source: Tree,
                                childIndices: Array[Int],
                                children: Array[Object],
                                path: Array[Object],
                                treePath: TreePath)
  extends TreeModelChange(source, childIndices, children, path, treePath)

class TreeExpansion(override val source: Tree, val treePath: TreePath) extends TreeChange(source)

case class TreeCollapsed(override val source: Tree, override val treePath: TreePath)
  extends TreeExpansion(source, treePath)

case class TreeExpanded(override val source: Tree, override val treePath: TreePath)
  extends TreeExpansion(source, treePath)

case class TreeWillExpand(override val source: Tree, override val treePath: TreePath)
  extends TreeExpansion(source, treePath)

case class TreeWillCollapse(override val source: Tree, override val treePath: TreePath)
  extends TreeExpansion(source, treePath)

case class TreeSelection(
  override val source: Tree,
  newLeadSelectioPath: TreePath,
  oldLeadSelectionPath: TreePath,
  treePath: TreePath,
  treePaths: Array[TreePath],
  isAddedPath: Boolean)
  extends TreeChange(source)
