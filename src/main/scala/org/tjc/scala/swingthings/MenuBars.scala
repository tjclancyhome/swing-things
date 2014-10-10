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

import collection.mutable.HashMap
import swing.{ Action, Component, Menu, MenuBar, MenuItem }
import SwingThings.{ installedLookAndFeelNames, setLookAndFeelWithName, updateComponentTreeUI }
import scala.swing.UIElement
import scala.swing.MainFrame

class MenuEntry(val name: String, val menu: Menu) {
  private val subMenus = new HashMap[String, MenuEntry]()
  private var _parent: MenuEntry = null

  def +=(me: MenuEntry): this.type = {
    me.parent = this
    subMenus(me.name) = me
    this
  }

  def +=(m: Menu): this.type = {
    val me = new MenuEntry(m.name, m)
    me.parent = this
    subMenus(me.name) = me
    this
  }

  def parent = _parent
  def parent_=(me: MenuEntry) {
    _parent = me
  }

  override def toString = {
    subMenus.mkString(",")
  }
}

/** There was a good reason for attempting this.  The idea was that I wanted
  * to build the menu bar and add the behaviour (actions) later. So this was
  * kind of a failed experiment.
  *
  * @author Thomas
  *
  */
class MenuBars(val menuBar: MenuBar) {
  private val menus = HashMap[String, MenuEntry]()

  def +=(me: MenuEntry): this.type = {
    menus(me.name) = me
    this
  }

  def findMenu(name: String): Option[MenuEntry] = {
    if (menus.contains(name)) Some(menus(name)) else None
  }

  override def toString = {
    menus.mkString(",")
  }
}

object MenuBars {

  def apply(mb: MenuBar) = new MenuBars(mb)

  def makeLookAndFeelMenu(comp: MainFrame): Menu = {
    println("makeLookAndFeelMenu!")
    import SwingThings._
    val lAndFMenu = new Menu("Look and Feel") {
      val lAndFNames = installedLookAndFeelNames
      lAndFNames.foreach(n => {
        contents += new MenuItem(Action(n) {
          import scala.swing.Swing._
          onEDT {
            setLookAndFeelWithName(n)
            updateComponentTree(comp.self)
          }
        })
      })
    }
    lAndFMenu
  }
}