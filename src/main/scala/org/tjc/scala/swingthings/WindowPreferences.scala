package org.tjc.scala.swingthings

import java.util.prefs.Preferences
import java.awt.Point
import java.awt.Dimension
import java.io.ByteArrayOutputStream

class WindowPreferences(val appName: String) {
  private val prefs = Preferences.userRoot().node(appName.replace(" ", ""))
  prefs.sync()

  def windowWidth: Int = prefs.getInt("windowWidth", 640)
  def windowWidth_=(width: Int) { prefs.putInt("windowWidth", width); prefs.flush() }

  def windowHeight: Int = prefs.getInt("windowHeight", 480)
  def windowHeight_=(height: Int) { prefs.putInt("windowHeight", height); prefs.flush() }

  def windowX: Int = prefs.getInt("windowX", 0)
  def windowX_=(x: Int) { prefs.putInt("windowX", x); prefs.flush() }

  def windowY: Int = prefs.getInt("windowY", 0)
  def windowY_=(y: Int) { prefs.putInt("windowY", y); prefs.flush() }

  def point(): Point = new Point(windowX, windowY)
  def size(): Dimension = new Dimension(windowWidth, windowHeight)

  def toXmlString(): String = {
    val os = new ByteArrayOutputStream()
    prefs.exportSubtree(os)
    os.toString()
  }
  
  override def toString = s"WindowPreferences(width=$windowWidth, height=$windowHeight, x=$windowX, y=$windowY)"
}

object WindowPreferences {
  def apply(appName: String): WindowPreferences = new WindowPreferences(appName)
}