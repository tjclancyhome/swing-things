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
 * This file is a port of Java code from De Gregorio Daniele. His copyright
 * notice follows:
 * 
 ****************************************************************************
 * 
 *  Copyright 2010 De Gregorio Daniele.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.tjc.scala.swingthings

import java.awt.Point
import java.awt.Cursor
import scala.swing.Component
import scala.swing.event.MouseMoved
import scala.swing.event.MouseDragged
import java.awt.Graphics2D

/** A scala.swing Component that can be dragged about a panel.
  *
  */
class DraggableComponent(var draggable: Boolean, var overbearing: Boolean) extends Component {

  def this() {
    this(true, false)
  }

  private final val DefaultDraggingCursor: Cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
  private val handle: DraggableComponent = this
  private var dragCursor = DefaultDraggingCursor

  protected var anchorPoint: Point = new Point(-1, -1)
  protected def parent = peer.getParent()

  def draggingCursor: Cursor = dragCursor
  def draggingCursor_=(cursor: Cursor) { dragCursor = cursor }

  override def paint(g: Graphics2D) {
    super.paint(g);
    if (peer.isOpaque()) {
      g.setColor(background);
      g.fillRect(0, 0, size.width, size.height);
    }
  }

  listenTo(mouse.moves)

  reactions += {
    case MouseMoved(_, point, _) => {
      anchorPoint = point
      cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
    }
    case event: MouseDragged => {
      /*
       * I have no idea if any of this is going to work.  Ugh.
       */
      val anchorX = anchorPoint.x
      val anchorY = anchorPoint.y
      val parentOnScreen = parent.getLocationOnScreen()
      val mouseOnScreen = event.peer.getLocationOnScreen()
      val position = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY)
      peer.setLocation(position)
      if (overbearing) {
        parent.setComponentZOrder(handle.peer, 0);
        repaint();
      }
    }
  }

}

object DraggableComponent {
  def apply(draggable: Boolean = true, overbearing: Boolean = false) = new DraggableComponent(draggable, overbearing)
}