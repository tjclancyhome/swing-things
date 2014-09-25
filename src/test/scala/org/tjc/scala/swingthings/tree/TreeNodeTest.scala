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