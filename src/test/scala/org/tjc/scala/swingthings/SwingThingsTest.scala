package org.tjc.scala.swingthings

import org.junit._
import Assert._

@Test
class SwingThingsTest {
  import SwingThings._
  
  @Test
  def showInstalledLookAndFeels {
    installedLookAndFeels.foreach(println)
    println
  }
  
  @Test
  def showInstalledLookAndFeelNames {
    installedLookAndFeelNames.foreach(println)
    println
  }
  
  @Test
  def showInstalledLookAndFeelClassNames {
    installedLookAndFeelClassNames.foreach(println)
    println
  }
}