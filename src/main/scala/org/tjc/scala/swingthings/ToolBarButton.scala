package org.tjc.scala.swingthings

import scala.swing.Action
import scala.swing.Button
import javax.swing.ImageIcon

class ToolBarButton(val imageName: String, val tooltipText: String = "", val altText: String = "", val action: Action = Action(""){}) {
  val button = new Button(action) {
      val buttonUrl = ClassLoader.getSystemResource("images/" + imageName)
      if (buttonUrl != null) {
      icon = new ImageIcon(buttonUrl)
      }
      else {
        text = altText
        System.err.println(s"Resource not found: $imageName")
      }
      tooltip = tooltipText    
  }
}

object ToolBarButton {
  def apply(imageName: String)(op: => Unit) = new ToolBarButton(imageName, action=Action("")(op))
  def apply(imageName: String, tooltipText: String)(op: => Unit) = new ToolBarButton(imageName, tooltipText, action = Action("")(op))
  def apply(imageName: String, tooltipText: String, altText: String)(op: => Unit) = {
    new ToolBarButton(imageName, tooltipText, altText, Action("")(op))
  }
  
  implicit def toolBarButton2Button(b: ToolBarButton): Button = b.button
}