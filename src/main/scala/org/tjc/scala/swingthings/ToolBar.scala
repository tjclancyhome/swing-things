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
import java.awt.Dimension
import javax.accessibility.AccessibleContext

/** A simple wrapper around JToolBar.
  *
  * @author Thomas
  *
  */
class ToolBar(val toolbarName: String, val orient: Int = SwingConstants.HORIZONTAL) extends Component with SequentialContainer.Wrapper {
  override lazy val peer: JToolBar = new JToolBar(toolbarName, orient) with SuperMixin

  def this(orientation: Int) = this("default", orientation)
  def this() = this("default")

  def margin: Insets = peer.getMargin
  def margin_=(insets: Insets) { peer.setMargin(insets) }
  def floatable: Boolean = peer.isFloatable
  def floatable_=(b: Boolean) { peer.setFloatable(b) }
  def rollover: Boolean = peer.isRollover
  def rollover_=(b: Boolean) { peer.setRollover(b) }
  def orientation: Int = peer.getOrientation()
  def orientation_=(orient: Int) { peer.setOrientation(orient) }
  def addSeparator() { peer.addSeparator() }
  def addSeparator(dim: Dimension) { peer.addSeparator(dim) }
  def accessibleContext: AccessibleContext = peer.getAccessibleContext()
}

/** @author Thomas
  *
  */
object ToolBar {
  case object NoToolBar extends ToolBar

  def apply(toolBarName: String): ToolBar = new ToolBar(toolBarName)
  def apply(toolBarName: String, orientation: Int): ToolBar = new ToolBar(toolBarName, orientation)

  /** A factory method that creates a button for the toolbar.
    *
    * @param imageFileName
    * @param action
    * @param tooltip
    * @return
    */
  def toolbarButton(imageFileName: String, tooltipText: String = "", action: Action): Button = {
    new Button(action) {
      val buttonUrl = ClassLoader.getSystemResource("images/" + imageFileName)
      icon = new ImageIcon(buttonUrl)
      tooltip = tooltipText
    }
  }

  /** Another factory method that lets you at the action body to the end of the method call.
    *
    * @param imageFileName
    * @param tooltipText
    * @param altText
    * @param op
    * @return
    */
  def makeToolBarButton(imageFileName: String, tooltipText: String = "", altText: String = "")(op: => Unit): Button = {
    new Button(Action("")(op)) {
      val buttonUrl = ClassLoader.getSystemResource("images/" + imageFileName)
      if (buttonUrl != null) {
        icon = new ImageIcon(buttonUrl)
      }
      else {
        text = altText
        System.err.println(s"Resource not found: $imageFileName")
      }
      tooltip = tooltipText
    }
  }
}