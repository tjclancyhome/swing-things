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

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Image
import java.awt.Point
import java.awt.image.BufferedImage
import java.awt.image.ImageObserver
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.border.LineBorder
import javax.swing.border.Border
import javax.swing.border.BevelBorder

/** @author Thomas
  *
  */
class DraggableImageComponent extends DraggableComponent with ImageObserver {

  private var autoSz: Boolean = false
  private var autoSizeDimension: Dimension = new Dimension(0, 0)

  /*
   * I know, I know. Nulls are bad in Scala!
   */
  protected var img: Image = null

  background = Color.black
  peer.setLayout(null)

  def autoSize: Boolean = autoSz
  def autoSize_=(as: Boolean) { autoSz = as }
  def image: Image = img
  def image_=(i: Image) { img = i }

  override def paint(g: Graphics2D) {
    super.paint(g)
    g.clearRect(0, 0, size.width, size.height)
    if (img != null) {
      g.drawImage(img, 0, 0, size.width, size.height, peer)
    }
    else {
      g.setColor(background)
      g.fillRect(0, 0, size.width, size.height)
    }
  }

  override def imageUpdate(img: Image, infoflags: Int, x: Int, y: Int, w: Int, h: Int): Boolean = {
    if (infoflags == ImageObserver.ALLBITS) {
      repaint()
      setAutoSizeDimension();
      false;
    }
    else true;
  }

  /** It is used to Resize component when it has an AutoSize value setted on TRUE
    *
    * @param pixels
    */
  def grow(pixels: Int) {
    val ratio = size.getWidth() / size.getHeight();
    peer.setSize(size.width + pixels, (size.height + (pixels / ratio)).toInt);
  }

  /** This method is used to resize component considering w/h ratio of image.
    */
  private def setAutoSizeDimension() {
    if (!autoSize) {
      return
    }
    if (image != null) {
      if (image.getHeight(null) == 0 || size.height == 0) {
        return
      }
      if ((size.width / size.height) == (image.getWidth(null) / (image.getHeight(null)))) {
        return
      }
      autoSizeDimension = adaptDimension(new Dimension(image.getWidth(null), image.getHeight(null)), size);
      peer.setSize(autoSizeDimension.width, autoSizeDimension.height);
      repaint
    }
  }

  /** It is a simple tecnique to retrieve dimensions of Image, preserving ratio
    * w/h of image and make a best matching on the parent box.
    *
    * @param source
    * @param dest
    * @return
    */
  private def adaptDimension(source: Dimension, dest: Dimension): Dimension = {
    var sW = source.width
    var sH = source.height
    var dW = dest.width
    var dH = dest.height
    var ratio = (sW.toDouble) / (sH.toDouble)
    if (sW >= sH) {
      sW = dW
      sH = (sW / ratio).toInt
    }
    else {
      sH = dH
      sW = (sH * ratio).toInt
    }
    new Dimension(sW, sH)
  }

}

object DraggableImageComponent {
  def apply(): DraggableImageComponent = new DraggableImageComponent

  def apply(imageName: String, loc: Point, bordr: Border = new LineBorder(Color.black, 1)): DraggableImageComponent = {
    new DraggableImageComponent {
      val buffImg: BufferedImage = ImageIO.read(getResource(imageName));
      image = buffImg
      autoSize = true
      overbearing = false
      border = bordr
      peer.setSize(buffImg.getWidth(), buffImg.getHeight());
      peer.setLocation(loc);
    }
  }

  def getResource(name: String): URL = {
    ClassLoader.getSystemResource(name);
  }

}