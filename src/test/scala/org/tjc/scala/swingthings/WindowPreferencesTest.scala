package org.tjc.scala.swingthings

import org.junit._
import Assert._

@Test
class WindowPreferencesTest {

  @Test
  def smoke() {
    val winPrefs = WindowPreferences("WindowPreferencesTest")
    winPrefs.windowWidth = 1024
    winPrefs.windowHeight = 768
    winPrefs.windowX = 5
    winPrefs.windowY = 5
    println(winPrefs)
    println(winPrefs.toXmlString)
  }
}