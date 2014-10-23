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

import swing.MainFrame

import org.slf4j.LoggerFactory

import com.typesafe.scalalogging.Logger

/** Extends MainFrame and adds automatic window size and position tracking via
  * the WindowsPreferences class.
  *
  * @author Thomas
  *
  */
class SwingThingsMainFrame(val appName: String = "SwingThings") extends MainFrame {
  private val logger = Logger(LoggerFactory.getLogger(getClass()))
  private var preserve = true

  title = appName;

  logger.debug(s"Initializing application: $appName")

  val windowPrefs = WindowPreferences(appName)

  def preserveState: Boolean = preserve
  def preserveState_=(preserve: Boolean) { this.preserve = preserve }

  /** Override this to preseve any other preferences you might wish to save. You'll then need to
    * override the initializeUserWindowPrefs() method to use them.
    *
    * @param prefs
    */
  protected def preserveUserWindowPrefs(prefs: WindowPreferences) {}

  protected def preserveWindowState() {
    val b = getBounds()
    windowPrefs.windowWidth = b.width
    windowPrefs.windowHeight = b.height
    windowPrefs.windowX = b.x
    windowPrefs.windowY = b.y
    windowPrefs.lookAndFeelName = SwingThings.currentLookAndFeelName
  }

  def quit() {
    logger.debug("preserving window state...")
    if (preserveState) {
      preserveWindowState()
      preserveUserWindowPrefs(windowPrefs)
    }
    logger.debug("exiting...")
    sys.exit(0)
  }

  override def closeOperation() {
    quit()
  }

  def mainFrame = this

  def setLookAndFeel(name: String) {
    import scala.swing.Swing._
    import SwingThings._
    onEDT {
      setLookAndFeelWithName(name)
      updateComponentTree(this.self)
    }
  }

  private def getBounds(): Rectangle = bounds

  /** If you have overriden the preserveUserWindowPrefs then you need to override
    * this method to make use of them on startup.
    *
    * @param prefs
    */
  protected def initializeUserWindowPrefs(prefs: WindowPreferences) {}

  /** Initialize the window prefs.
    */
  protected def initializeWindowPrefs() {
    if (preserveState) {
      location = windowPrefs.point()
      size = windowPrefs.size
      setLookAndFeel(windowPrefs.lookAndFeelName)
      initializeUserWindowPrefs(windowPrefs)
    }
  }

  initializeWindowPrefs()
}