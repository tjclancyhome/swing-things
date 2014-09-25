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