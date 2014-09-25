package org.tjc.scala.swingthings

import javax.swing.SwingWorker

class Task(t: => Unit, d: => Unit) extends SwingWorker[Unit,Unit] {
  override def doInBackground() {t}  
  override def done() {d}
}

object Task {
  def apply(t: => Unit, d: => Unit = {}) = new Task(t, d)
}