package org.tjc.scala.swingthings

import scala.swing.Component
import scala.swing.Frame

/** Similar in some respects to javax.swing.UIManager with some more
 *  convenience methods
 *
 *  @author Thomas
 *
 */
object SwingThings {
  import javax.swing.UIManager
  import javax.swing.SwingWorker

  /*
   * Known constants
   */
  final val Metal: String = "Metal"
  final val Nimbus: String = "Nimbus"
  final val Motif: String = "CDE/Motif"
  final val Windows: String = "Windows"
  final val WindowsClassic = "Windows Classic"

  /** A case class that acts as the java swing UIManager.LookAndFeelInfo
   *
   *  @author Thomas
   *
   */
  case class LookAndFeelInfo(val name: String, val className: String)

  def installedLookAndFeels(): List[LookAndFeelInfo] = {
    for (i <- UIManager.getInstalledLookAndFeels().toList) yield LookAndFeelInfo(i.getName(), i.getClassName())
  }

  def installedLookAndFeelNames(): List[String] = {
    for (i <- installedLookAndFeels) yield i.name
  }

  def installedLookAndFeelClassNames(): List[String] = {
    for (i <- installedLookAndFeels) yield i.className
  }

  /*
   * A set of simple method for setting the UI look and feel
   */

  def setSystemLookAndFeel() { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) }
  def setCrossPlatformLookAndFeel() { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()) }
  def setLookAndFeel(className: String) { UIManager.setLookAndFeel(className) }
  def setLookAndFeelWithName(name: String) { UIManager.setLookAndFeel(getLookAndFeelInfoByName(name).className) }
  def setLookAndFeel(info: LookAndFeelInfo) { setLookAndFeel(info.className) }

  /** This does a case-insensitive lookup but the lAndF name
   *  @param lAndFName
   *  @return
   */
  def getLookAndFeelInfoByName(lAndFName: String): LookAndFeelInfo = {
    val l = installedLookAndFeels.find(l => { l.name.toLowerCase() == lAndFName.toLowerCase() })
    l match {
      case Some(i) => i
      case None    => throw new IllegalArgumentException(s"Found no look and feel installed with name $lAndFName")
    }
  }

  def updateComponentTreeUI(comp: Component) {
    import javax.swing.SwingUtilities
    SwingUtilities.updateComponentTreeUI(comp.self)
  }

  def task(t: => Unit, d: => Unit = {}): Task = Task(t, d)

  def work(bkgTask: => Unit, done: => Unit = {}): Unit = {
    val sw = new SwingWorker[Unit, Unit]() {
      override def doInBackground(): Unit = {
        bkgTask
      }
      override def done(): Unit = {
        done
      }

    }
    sw.execute()
  }
}