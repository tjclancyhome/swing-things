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

import javax.swing.SwingWorker

/** A small wrapper that wraps SwingWorker.
 *
 *  @author Thomas
 *
 */
class Task(t: => Unit, d: => Unit) extends SwingWorker[Unit, Unit] {
  override def doInBackground() { t }
  override def done() { d }
}

object Task {
  def apply(t: => Unit, d: => Unit = {}) = new Task(t, d)
}