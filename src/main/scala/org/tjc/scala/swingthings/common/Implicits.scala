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

package org.tjc.scala.swingthings.common

import java.util.Properties

/** @author Thomas
 *
 */
object Implicits {

  final val Endl: Char = '\n'
  final val Tab: Char = '\t'

  /** Adds methods to StringBuilder.
   *  
   *  @author Thomas
   *
   */
  implicit class StringBuilderCoolness(val sb: StringBuilder) {
    def appendNewLine() = sb.append('\n')
    def <<(any: Any) = sb.append(any.toString)
  }

  /** Adds conversion methods to java.util.Properties.
   *  
   *  @author Thomas
   *
   */
  implicit class PropertyConverters(p: Properties) {
    
    def getBooleanProperty(propName: String): Boolean = {
      p.getProperty(propName).toLowerCase() match {
        case "true" => true
        case "t"    => true
        case "yes"  => true
        case "y"    => true
        case "1"    => true
        case _      => false
      }
    }
    
    def getBooleanProperty(propName: String, default: Boolean): Boolean = {
      if(p.containsKey(propName)) getBooleanProperty(propName) else default
    }   
    
    def setBooleanProperty(propName: String, value: Boolean) {
      p.setProperty(propName, value.toString)
    }

    def getIntProperty(propName: String): Int = {
      p.getProperty(propName).toInt
    }

    def getIntProperty(propName: String, default: Int): Int = {
      if(p.containsKey(propName)) getIntProperty(propName) else default
    }
    
    def setIntProperty(propName: String, value: Int) {
      p.setProperty(propName, value.toString)
    }
    
    def getDoubleProperty(propName: String): Double = {
      p.getProperty(propName).toDouble
    }

    def getIntProperty(propName: String, default: Double): Double = {
      if(p.containsKey(propName)) getDoubleProperty(propName) else default
    }
    
    def setDoubleProperty(propName: String, value: Double) {
      p.setProperty(propName, value.toString)
    }
  }

}