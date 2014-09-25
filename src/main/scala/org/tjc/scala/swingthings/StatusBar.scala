package org.tjc.scala.swingthings

import scala.swing._
import BorderPanel.Position._
import javax.swing.border.EtchedBorder

class StatusBar(val width: Int = 640, val height: Int = 25) extends BorderPanel {
  private var message = ""
  private val label = new Label(message) 
  private var pb = new ProgressBar {
    visible = false
    paintBorder(false)
    min = 0
    max = 100
  }
  
  preferredSize= new Dimension(640, height)

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