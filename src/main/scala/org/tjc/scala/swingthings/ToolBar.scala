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

import swing.{ Action, Button, Component, Insets, SequentialContainer }

import javax.swing.{ ImageIcon, JToolBar, SwingConstants }

/** A simple wrapper around JToolBar.
 *
 *  @author Thomas
 *
 */
class ToolBar(val toolbarName: String, val orientation: Int) extends Component with SequentialContainer.Wrapper {
  override lazy val peer: JToolBar = new JToolBar(toolbarName, orientation) with SuperMixin

  def this(toolbarName: String) = this(toolbarName, SwingConstants.HORIZONTAL)
  def this(orientation: Int) = this("default", orientation)
  def this() = this(SwingConstants.HORIZONTAL)

  /*
   * Some convenient wrapper method.
   */

  def margin: Insets = peer.getMargin
  def margin_=(insets: Insets) { peer.setMargin(insets) }
  def floatable: Boolean = peer.isFloatable
  def floatable_=(b: Boolean) { peer.setFloatable(b) }
  def rollover: Boolean = peer.isRollover
  def rollover_=(b: Boolean) { peer.setRollover(b) }
  def addSeparator() { peer.addSeparator() }

}

/** @author Thomas
 *
 */
object ToolBar {
  case object NoToolBar extends ToolBar

  /** A factory method that creates a button for the toolbar.
   *
   *  @param imageFileName
   *  @param action
   *  @param tooltip
   *  @return
   */
  def toolbarButton(imageFileName: String, action: Action, tooltip: String): Button = {
    new Button(action) {
      val buttonUrl = ClassLoader.getSystemResource("images/" + imageFileName)
      icon = new ImageIcon(buttonUrl, tooltip)
    }
  }
}