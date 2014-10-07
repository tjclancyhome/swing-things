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

import org.junit._
import Assert._

@Test
class TreeNodeTest {
  import scala.collection.JavaConversions._

  val root = new TreeNode("Root") {
    add(new TreeNode("Foo") {
      add(new TreeNode("Child Foo") {
        add(new TreeNode("Child Child Foo"))
      })
    })
    add(new TreeNode("Bar") {
      add(new TreeNode("Child Bar") {
        add(new TreeNode("Child Child Bar"))
      })
    })
    add(new TreeNode("Baz") {
      add(new TreeNode("Child Baz") {
        add(new TreeNode("Child Child Baz"))
      })
    })
  }

  @Test
  def validateChildren() {
    assertEquals("Foo", root.firstChild.toString())
    assertEquals("Bar", root.childAfter(root.firstChild).toString)
    assertEquals("Baz", root.childAfter(root.childAfter(root.firstChild)).toString)
    assertEquals("Child Foo", root.firstChild.firstChild.toString())
  }

  @Test
  def printTreeStructure() {
    println(root.toFormattedString)
  }

  @Test
  def treeNodeOps() {
    assertEquals(3, root.childCount)
    assertEquals(3, root.leafCount)
    assertEquals(1, root.siblingCount)
    assertEquals(3, root.depth)
  }
}