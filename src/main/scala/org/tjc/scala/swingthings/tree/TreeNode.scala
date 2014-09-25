package org.tjc.scala.swingthings.tree

import javax.swing.tree.DefaultMutableTreeNode
import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

class TreeNode(val data: Any) {
  private val children = new ArrayBuffer[TreeNode]()

  lazy val peer: DefaultMutableTreeNode = new DefaultMutableTreeNode(data)

  def +=(thatNode: TreeNode): this.type = {
    require(thatNode != this)
    children += thatNode
    peer.add(thatNode.peer)
    this
  }

  override def toString = {
    peer.breadthFirstEnumeration().mkString("\n  -> ")
  }
}

object TreeNode {
  def apply(): TreeNode = new TreeNode()
  def apply(any: Any): TreeNode = new TreeNode(any)
}

