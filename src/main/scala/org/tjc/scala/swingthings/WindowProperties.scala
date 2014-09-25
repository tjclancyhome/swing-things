package org.tjc.scala.swingthings

import java.awt.Dimension
import java.awt.Point
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files.createDirectories
import java.nio.file.Files.exists
import java.nio.file.Paths

import scala.util.Properties.userHome

import org.tjc.scala.common.Implicits.PropertyConverters
import org.tjc.scala.common.using

/** A simple class used for maitaining a window's state.
 *
 *  @author Thomas
 *
 */
class WindowProperties(val appName: String) {

  private final val WindowWidth = "window.size.width"
  private final val WindowHeight = "window.size.height"
  private final val WindowX = "window.pos.x"
  private final val WindowY = "window.pos.y"

  private val configFilename = "window.properties"
  private val configFilePath = Paths.get(userHome, ".config", appName.replaceAll(" ", ""))
  private val configFile = new File(configFilePath.toFile(), configFilename)
  private val props = new java.util.Properties()

  if (!exists(configFilePath)) {
    createDirectories(configFilePath)
  }

  if (!configFile.exists()) {
    /*
     * Defaults
     */
    windowWidth = 640
    windowHeight = 480
    windowX = 0
    windowY = 0
    save()
  }
  else {
    load()
  }

  def windowWidth: Int = props.getIntProperty(WindowWidth, 640)
  def windowWidth_=(width: Int) { props.setIntProperty(WindowWidth, width) }

  def windowHeight: Int = props.getIntProperty(WindowHeight, 480)
  def windowHeight_=(height: Int) { props.setIntProperty(WindowHeight, height) }

  def windowX: Int = props.getIntProperty(WindowX, 0)
  def windowX_=(x: Int) { props.setIntProperty(WindowX, x) }

  def windowY: Int = props.getIntProperty(WindowY, 0)
  def windowY_=(y: Int) { props.setIntProperty(WindowY, y) }

  def point(): Point = new Point(windowX, windowY)
  def size(): Dimension = new Dimension(windowWidth, windowHeight)

  def save() {
    using(new FileOutputStream(configFile)) { file =>
      props.store(file, "")
    }
  }

  override def toString = s"WindowConfig(width=$windowWidth, height=$windowHeight, x=$windowX, y=$windowY)"

  private def load() {
    using(new FileInputStream(configFile)) { file =>
      props.load(file)
    }
  }

}

object WindowProperties {
  def apply(appName: String): WindowProperties = new WindowProperties(appName)
}