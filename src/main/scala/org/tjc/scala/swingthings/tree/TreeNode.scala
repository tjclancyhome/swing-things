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

package org.tjc.scala.swingthings.tree

import collection.JavaConversions.enumerationAsScalaIterator
import collection.mutable.ArrayBuffer

import javax.swing.tree.DefaultMutableTreeNode

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

