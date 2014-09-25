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

import java.awt.Rectangle

import scala.swing.MainFrame
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

class SwingThingsMainFrame(val appName: String = "SwingThings") extends MainFrame {
  private val logger = Logger(LoggerFactory.getLogger(getClass()))
  private var preserve = true
  
  title = appName;
  
  logger.debug(s"Initializing application: $appName")
  
  val windowPrefs = WindowPreferences(appName)

  def preserveState: Boolean = preserve
  def preserveState_=(preserve: Boolean) { this.preserve = preserve }

  protected def preserveWindowState() {
    if (preserveState) {
      val b = getBounds()
      windowPrefs.windowWidth = b.width
      windowPrefs.windowHeight = b.height
      windowPrefs.windowX = b.x
      windowPrefs.windowY = b.y
    }
  }

  def quit() {
    logger.debug("preserving window state...")
    preserveWindowState()
    logger.debug("exiting...")
    sys.exit(0)
  }

  override def closeOperation() {
    quit()
  }

  private def getBounds(): Rectangle = bounds
}