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

import java.awt.{ Dimension, Point }
import java.io.ByteArrayOutputStream
import java.util.prefs.Preferences
import SwingThings._

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

  def lookAndFeelName: String = prefs.get("lookAndFeel", Metal)
  def lookAndFeelName_=(name: String) { prefs.put("lookAndFeel", name) }

  /*
   * User-defined preferences
   */

  def put(key: String, value: String) { prefs.put(key, value) }
  def get(key: String, defValue: String): String = prefs.get(key, defValue)
  def putBoolean(key: String, value: Boolean) { prefs.putBoolean(key, value) }
  def getBoolean(key: String, defValue: Boolean): Boolean = prefs.getBoolean(key, defValue)
  def putInt(key: String, value: Int) { prefs.putInt(key, value) }
  def getInt(key: String, defValue: Int): Int = prefs.getInt(key, defValue)
  def putLong(key: String, value: Long) { prefs.putLong(key, value) }
  def getLong(key: String, defValue: Long): Long = prefs.getLong(key, defValue)
  def putDouble(key: String, value: Double) { prefs.putDouble(key, value) }
  def getDouble(key: String, defValue: Double): Double = prefs.getDouble(key, defValue)
  def putFloat(key: String, value: Float) { prefs.putFloat(key, value) }
  def getFloat(key: String, defValue: Float): Float = prefs.getFloat(key, defValue)

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