package org.tjc.scala.swingthings.tree

import org.junit._
import Assert._

@Test
class TreeNodeTest {
  import scala.collection.JavaConversions._

  @Test
  def smoke() {
    val root = TreeNode("Root")
    val child1 = TreeNode("Child 1")
    root += child1
    val child2 = new TreeNode("Child 2")
    root += child2
    val grandChild1 = new TreeNode("Grandchild 1")
    child1 += grandChild1
    val grandChild2 = new TreeNode("Grandchild 2")
    child2 += grandChild2
    println(root)
  }
}