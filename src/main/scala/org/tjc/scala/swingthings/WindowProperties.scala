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

import java.awt.{Dimension, Point}
import java.io.{File, FileInputStream, FileOutputStream}
import java.nio.file.Files.{createDirectories, exists}
import java.nio.file.Paths

import util.Properties.userHome

import org.tjc.scala.swingthings.common.Implicits.PropertyConverters
import org.tjc.scala.swingthings.common.using

/** A simple class used for maitaining a window's state.
 *  
 *  See the WindowsPreferences class for a simpler, platform independent
 *  way to store properties.
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