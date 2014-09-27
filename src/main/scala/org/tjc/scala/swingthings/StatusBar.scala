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

import swing.{ Dimension, Label, ProgressBar }
import swing.BorderPanel
import swing.BorderPanel.Position.{ East, West }

import javax.swing.border.EtchedBorder

/** A simple status bar control that embeds a message and a
 *  progress bar.
 *
 *  @author Thomas
 *
 */
class StatusBar(val width: Int = 640, val height: Int = 25) extends BorderPanel {
  private var message = ""
  private val label = new Label(message)
  private var pb = new ProgressBar {
    visible = false
    paintBorder(false)
    min = 0
    max = 100
  }

  preferredSize = new Dimension(640, height)

  layout(label) = West
  layout(pb) = East

  border = new EtchedBorder(EtchedBorder.LOWERED)

  def statusMessage = message
  def statusMessage_=(msg: String) {
    message = msg
    label.text = message
  }

  def progressBar = pb
  def progressBar_=(pb: ProgressBar) {
    this.pb = pb
    this.pb.visible = true
  }

  def showProgressBar {
    progressBar.visible = true
    statusMessage = "Loading file..."
    this.revalidate
  }

  def hideProgressBar {
    progressBar.visible = false
    statusMessage = ""
    this.revalidate
  }
}

object StatusBar {
  def apply() = new StatusBar
  def apply(width: Int, height: Int) = new StatusBar(width, height)
}