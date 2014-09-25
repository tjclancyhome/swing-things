package org.tjc.scala.swingthings

import scala.swing._
import scala.swing.event._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.TreeSet
import scala.collection.mutable.HashMap

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
  
  def makeLookAndFeelMenu(comp: Component): Menu = {
    import SwingThings._
    val lAndFMenu = new Menu("Look and Feel") {
      val lAndFNames = installedLookAndFeelNames
      lAndFNames.foreach(n => {
        contents += new MenuItem(Action(n) {
          setLookAndFeelWithName(n)
          updateComponentTreeUI(comp)
        })
      })
    }
    lAndFMenu
  }  
}