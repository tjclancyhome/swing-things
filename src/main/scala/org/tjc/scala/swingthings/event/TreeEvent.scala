/*
 * Copyright 2014 Thomas J. Clancy
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.tjc.scala.swingthings.event

import swing.event.ComponentEvent
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
