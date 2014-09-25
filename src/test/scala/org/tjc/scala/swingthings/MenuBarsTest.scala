package org.tjc.scala.swingthings

import org.junit._
import Assert._
import org.tjc.scala.testmetrics.UnitTestSupport
import scala.swing.MenuBar
import scala.swing.Menu

@Test
class MenuBarsTest extends UnitTestSupport {

  @Test
  def appendMenuItem() {
    println(makeBanner("appendMenuItem()"))
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