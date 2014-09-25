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