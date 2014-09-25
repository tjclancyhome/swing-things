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