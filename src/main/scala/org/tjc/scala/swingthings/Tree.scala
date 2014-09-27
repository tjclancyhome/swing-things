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

package org.tjc.scala.swingthings

import java.io.{PrintStream, PrintWriter}

import swing.{Component, Publisher}

import org.slf4j.LoggerFactory
import org.tjc.scala.swingthings.tree.TreeNode

import com.typesafe.scalalogging.Logger

import event.{TreeCollapsed, TreeExpanded, TreeNodesChanged, TreeNodesInserted, TreeNodesRemoved, TreeSelection, TreeStructureChanged, TreeWillCollapse, TreeWillExpand}
import javax.swing.JTree
import javax.swing.event.{TreeExpansionEvent, TreeExpansionListener, TreeModelEvent, TreeModelListener, TreeSelectionEvent, TreeSelectionListener, TreeWillExpandListener}
import javax.swing.tree.{DefaultTreeModel, TreeModel}

/** A simple wrapper around JTree.
 *
 *  I studied scala.swing.Table as a guide for creating this class. Currently
 *  my understanding is a bit shakey so this will certainly be far from
 *  perfect.
 *
 *  @author Thomas
 *
 */
class Tree extends Component with Publisher {
  private val logger = Logger(LoggerFactory.getLogger(getClass()))

  override lazy val peer: JTree = new JTree() with SuperMixin

  model = new DefaultTreeModel(TreeNode("root").peer)

  def this(node: TreeNode) {
    this()
    model = new DefaultTreeModel(node.peer)
  }

  def this(m: TreeModel) {
    this()
    model = m
  }

  def list(printWriter: PrintWriter) { peer.list(printWriter) }
  def list(printStream: PrintStream) { peer.list(printStream) }

  def model: DefaultTreeModel = {
    logger.debug("getting model.")
    peer.getModel().asInstanceOf[DefaultTreeModel]
  }

  def model_=(m: TreeModel) = {
    logger.debug(s"setting tree model: $m")
    peer.setModel(m)
    model.removeTreeModelListener(modelListener)
    model.addTreeModelListener(modelListener)
  }

  def root_=(rootNode: TreeNode) {
    model.setRoot(rootNode.peer)
  }

  protected val modelListener = new TreeModelListener {
    def treeNodesChanged(e: TreeModelEvent) {
      publish(TreeNodesChanged(Tree.this, e.getChildIndices(), e.getChildren(), e.getPath(), e.getTreePath()))

    }
    def treeNodesInserted(e: TreeModelEvent) {
      publish(TreeNodesInserted(Tree.this, e.getChildIndices(), e.getChildren(), e.getPath(), e.getTreePath()))
    }
    def treeNodesRemoved(e: TreeModelEvent) {
      publish(TreeNodesRemoved(Tree.this, e.getChildIndices(), e.getChildren(), e.getPath(), e.getTreePath()))
    }
    def treeStructureChanged(e: TreeModelEvent) {
      publish(TreeStructureChanged(Tree.this, e.getChildIndices(), e.getChildren(), e.getPath(), e.getTreePath()))
    }
  }

  override protected def onFirstSubscribe() {
    super.onFirstSubscribe()
    val treeExpansionListener = new TreeExpansionListener {
      def treeCollapsed(e: TreeExpansionEvent) {
        publish(TreeCollapsed(Tree.this, e.getPath()))
      }
      def treeExpanded(e: TreeExpansionEvent) {
        publish(TreeExpanded(Tree.this, e.getPath()))
      }
    }
    peer.addTreeExpansionListener(treeExpansionListener)

    val treeSelectionListener = new TreeSelectionListener {
      def valueChanged(e: TreeSelectionEvent) {
        publish(TreeSelection(
          Tree.this,
          e.getNewLeadSelectionPath(),
          e.getOldLeadSelectionPath(),
          e.getPath(),
          e.getPaths(),
          e.isAddedPath()))
      }
    }
    peer.addTreeSelectionListener(treeSelectionListener)

    val treeWillExpandListener = new TreeWillExpandListener {
      def treeWillCollapse(e: TreeExpansionEvent) {
        publish(TreeWillCollapse(Tree.this, e.getPath()))
      }
      def treeWillExpand(e: TreeExpansionEvent) {
        publish(TreeWillExpand(Tree.this, e.getPath()))
      }
    }
    peer.addTreeWillExpandListener(treeWillExpandListener)
  }

}

/** @author Thomas
 *
 */
object Tree {
  case object NoTree extends Tree
  def apply(): Tree = new Tree()
  def apply(node: TreeNode): Tree = new Tree(node)
  def apply(model: TreeModel): Tree = new Tree(model)
}