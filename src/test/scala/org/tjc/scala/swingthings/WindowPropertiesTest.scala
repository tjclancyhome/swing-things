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

import org.junit._
import Assert._

@Test
class WindowConfigTest {

  @Test
  def smoke() {
    val props = new WindowProperties("test")
    props.windowHeight = 1000
    props.save
    println(s"config.windowHeight: ${props.windowHeight}")
    println(s"config.windowWidth : ${props.windowWidth}")
    println(s"config.windowX     : ${props.windowX}")
    println(s"config.windowY     : ${props.windowY}")
    println(s"config.point       : ${props.point}")
    println(s"config.size        : ${props.size}")
  }
}