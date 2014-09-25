package org.tjc.scala.swingthings

package object common {
  
  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try {
      f(resource)
    }
    finally {
      resource.close()
    }
  }
}