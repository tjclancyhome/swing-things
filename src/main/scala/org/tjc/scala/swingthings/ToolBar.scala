package org.tjc.scala.swingthings

import swing._
import swing.event._
import javax.swing.{ JToolBar, SwingConstants, ImageIcon }

/** A simple wrapper around JToolBar.
 *
 *  @author Thomas
 *
 */
class ToolBar(val tbName: String, val orientation: Int) extends Component with SequentialContainer.Wrapper {
  override lazy val peer: JToolBar = new JToolBar(tbName, orientation) with SuperMixin

  def this(tbName: String) = this(tbName, SwingConstants.HORIZONTAL)
  def this(orientation: Int) = this("", orientation)
  def this() = this("", SwingConstants.HORIZONTAL)

  /*
   * Some convenient wrapper method.
   */

  def margin: Insets = peer.getMargin
  def margin_=(insets: Insets) = peer.setMargin(insets)
  def floatable: Boolean = peer.isFloatable
  def floatable_=(b: Boolean) = peer.setFloatable(b)

}

/** @author Thomas
 *
 */
object ToolBar {
  case object NoToolBar extends ToolBar
  def toolbarButton(imageFileName: String, action: Action, tooltip: String): Button = {    
    new Button(action) {
      val buttonUrl = ClassLoader.getSystemResource("images/" + imageFileName)
      icon = new ImageIcon(buttonUrl, tooltip)
    }
  }
}