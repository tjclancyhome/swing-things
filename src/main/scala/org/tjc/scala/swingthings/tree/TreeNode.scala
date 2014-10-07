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

/** This extends java's DefaultMutableTreeNode and just adds some scala-like methods.
  * Originally I had this modeled after the scala-swing components where DefaultMutableTreeNode
  * was a peer object.  Obviously a mistake. Doh!
  *
  * @param node
  */
class TreeNode(val data: Any) extends DefaultMutableTreeNode(data) {

  def +=(thatNode: TreeNode): this.type = {
    require(thatNode != this)
    add(thatNode)
    this
  }

  def userData: Any = getUserObject()
  def userData_=(data: Any) = setUserObject(data)

  def childCount: Int = getChildCount()
  def depth: Int = getDepth()
  def leafCount: Int = getLeafCount()
  def level: Int = getLevel()
  def siblingCount: Int = getSiblingCount()
  def path: List[TreeNode] = {
    for(i <- getPath().toList) yield(i.asInstanceOf[TreeNode])
  }

  /** For debugging purposes.
    *
    */
  def showTreeNodes() {
    println(toFormattedString())
  }
  
  def toFormattedString(): String = {
    val sb = new StringBuilder()
    traverse(tn => {
      sb.append(s"""${"  " * tn.level}$tn""").append("\n")
    })
    sb.subSequence(0, sb.length-1).toString()
  }
  
  def traverse(f: (TreeNode) => Unit) {
    def loop(tn: TreeNode) {
      if (tn != null) {
        f(tn)
        if (tn.childCount > 0) {
          loop(tn.firstChild)
        }
        loop(tn.nextSibling)
      }
    }
    loop(this)    
  }
  
  def childAt(index: Int): TreeNode = getChildAt(index).asInstanceOf[TreeNode]
  def childAfter(node: TreeNode): TreeNode = getChildAfter(node).asInstanceOf[TreeNode]
  def childBefore(node: TreeNode): TreeNode = getChildBefore(node).asInstanceOf[TreeNode]
  def firstChild: TreeNode = getFirstChild().asInstanceOf[TreeNode]
  def firstLeaf: TreeNode = getFirstLeaf().asInstanceOf[TreeNode]
  def index(child: TreeNode) = getIndex(child)
  def lastChild: TreeNode = getLastChild().asInstanceOf[TreeNode]
  def lastLeaf: TreeNode = getLastLeaf().asInstanceOf[TreeNode]
  def nextLeaf: TreeNode = getNextLeaf().asInstanceOf[TreeNode]
  def nextNode: TreeNode = getNextNode().asInstanceOf[TreeNode]
  def nextSibling: TreeNode = getNextSibling().asInstanceOf[TreeNode]
  def previousLeaf: TreeNode = getPreviousLeaf().asInstanceOf[TreeNode]
  def previousNode: TreeNode = getPreviousNode().asInstanceOf[TreeNode]
  def previousSibling: TreeNode = getPreviousSibling().asInstanceOf[TreeNode]
  def root: TreeNode = getRoot().asInstanceOf[TreeNode]

}

object TreeNode {
  def apply(): TreeNode = new TreeNode()
  def apply(any: Any): TreeNode = new TreeNode(any)
}

