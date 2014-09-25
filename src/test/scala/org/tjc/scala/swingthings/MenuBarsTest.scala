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

import org.junit._
import Assert._
import scala.swing.MenuBar
import scala.swing.Menu

@Test
class MenuBarsTest {

  @Test
  def appendMenuItem() {
    val menuBars = MenuBars(new MenuBar())
    
    menuBars += new MenuEntry("File", new Menu("File"))
    
    println(menuBars)
    
    println(menuBars.findMenu("File").get)
    println(menuBars.findMenu("View"))
    
    val menuEntry = menuBars.findMenu("File").get
    menuEntry += new MenuEntry("Open", new Menu("Open"))
    println(menuEntry.parent)
    println(menuBars)
     
  }
}