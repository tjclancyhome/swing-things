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

import swing.{ Action, BorderPanel }
import swing.{ Button, Font, Insets, Menu, MenuBar, MenuItem, Orientation, ScrollPane, Separator, SimpleSwingApplication, SplitPane, TabbedPane }
import swing.BorderPanel.Position.{ Center, North, South }
import swing.TabbedPane.Page
import swing.TextArea

import org.slf4j.LoggerFactory
import org.tjc.scala.swingthings.event.{ TreeCollapsed, TreeExpanded, TreeSelection }
import org.tjc.scala.swingthings.tree.TreeNode

import com.typesafe.scalalogging.Logger

import SwingThings.{ Nimbus, installedLookAndFeelNames, setLookAndFeelWithName }
import ToolBar.toolbarButton

/** A test application that demonstrates the various controls, etc., in swing-things.
 *
 *  @author Thomas
 *
 */
object SwingThingsTestApp extends SimpleSwingApplication {
  import SwingThings._
  private val logger = Logger(LoggerFactory.getLogger(getClass()))
  
  System.setProperty("awt.useSystemAAFontSettings","on");
  System.setProperty("swing.aatext", "true");
  
  private val appTitle = "SwingThings Test App"

  setLookAndFeelWithName(Nimbus)
  //setSystemLookAndFeel()
  
  logger.debug(s"Starting application: $appTitle")

  def top = new SwingThingsMainFrame(appTitle) {

    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem(Action("New") {})
        contents += new MenuItem(Action("Open") {})
        contents += new Separator
        contents += new MenuItem(Action("Close") {
          quit()
        })
        contents += new Separator
        contents += new MenuItem(Action("Exit") {
          quit()
        })
      }
      contents += new Menu("Edit") {
        contents += new Menu("Foo") {
          contents += new Menu("Bar") {
            contents += new MenuItem("Baz")
          }
        }
      }
      contents += new Menu("View") {
        contents += new MenuItem(Action("Toggle Status Bar") {
          statusBar.visible = !statusBar.visible
        })
        contents += new Menu("Look and Feel") {
          installedLookAndFeelNames.foreach(n => {
            contents += new MenuItem(Action(n) {
              println(s"You selected look and feel: $n")
            })
          })
        }
      }
      contents += new Menu("Window")
      contents += new Menu("Help")
    }

    val button = new Button {
      text = "Press me!"
      enabled = true
    }

    val toolBar = new ToolBar {
      import ToolBar._
      contents += toolbarButton("new_document_24.png", Action("") {}, "Create a new file")
      contents += toolbarButton("open_document_24.png", Action("") {}, "Open a file")
      contents += toolbarButton("save_24.png", Action("") {}, "Save file")
      addSeparator()
      contents += toolbarButton("undo_24.png", Action("") {}, "Undo")
      contents += toolbarButton("redo_24.png", Action("") {}, "Redo")
      addSeparator()
      contents += toolbarButton("cut_clipboard_24.png", Action("") {}, "Cut")
      contents += toolbarButton("copy_clipboard_24.png", Action("") {}, "Cut")
      contents += toolbarButton("paste_clipboard_lined_24.png", Action("") {}, "Cut")
      addSeparator()
      contents += toolbarButton("search_24.png", Action("") {}, "Search")

      rollover = true
    }

    //    val tree = new Tree {
    //      
    //    }

    val tree = new Tree {
      val root = TreeNode("Root")
      val child1 = TreeNode("Child 1")
      root += child1
      val child2 = new TreeNode("Child 2")
      root += child2
      val grandChild1 = new TreeNode("Grandchild 1")
      child1 += grandChild1
      val grandChild2 = new TreeNode("Grandchild 2")
      child2 += grandChild2
      root_=(root)
      listenTo(mouse.clicks)

      reactions += {
        case TreeExpanded(tree, treePath)                                   => logger.debug(s"treeExpanded: treePath=$treePath, tree=$tree")
        case TreeCollapsed(tree, treePath)                                  => logger.debug(s"treeCollapsed: treePath=$treePath, tree=$tree")
        case TreeSelection(_, newSelPath, oldSelPath, treePath, _, isAdded) => logger.debug(s"treeSelection: $newSelPath, $oldSelPath, $treePath, $isAdded")
        //        case mc:MouseClicked => {
        //          if(SwingUtilities.isLeftMouseButton(mc.peer)) println("left mouse button clicked")
        //          if(SwingUtilities.isRightMouseButton(mc.peer)) println("right mouse buttong clicked")
        //          if(SwingUtilities.isMiddleMouseButton(mc.peer)) println("middle mouse buttong clicked")
        //        }
        //        case MouseClicked(source, point, mods, clicks, triggersPopup) => {
        //          println(s"self: $self")
        //          logger.debug(s"got mouse clicked at point=$point, mods=$mods, clicks=$clicks, triggersPopup=$triggersPopup, source=$source")          
        //        }
        //        case MousePressed(source, point, mods, clicks, triggersPopup) => {
        //          logger.debug(s"got mouse pressed at point=$point, mods=$mods, clicks=$clicks, triggersPopup=$triggersPopup, source=$source")
        //        }
        //        case MouseReleased(source, point, mods, clicks, triggersPopup) => {
        //          logger.debug(s"got mouse released at point=$point, mods=$mods, clicks=$clicks, triggersPopup=$triggersPopup, source=$source")
        //        }
        //        case something                     => logger.debug(s"Got this: $something")
      }
    }

    //deafTo(tree)
    listenTo(tree)

    val treeScroller = new ScrollPane {
      contents = tree
    }

    val treeTab = new TabbedPane {
      pages += new Page("Tree Explorer", treeScroller)
    }

    val textArea = new ScrollPane {
      contents = new TextArea {
        font = new Font("Consolas", 0, 14)
        peer.setMargin(new Insets(10, 10, 10, 10))
      }
    }

    val tabs = new TabbedPane {
      pages += new Page("A Text Area", textArea)
      pages += new Page("Another Text Area", new ScrollPane {
        contents = new TextArea {
          font = new Font("Consolas", 0, 14)
          peer.setMargin(new Insets(10, 10, 10, 10))
        }
      })
      pages += new Page("Another Tree", new ScrollPane {
        contents = new Tree(TreeNode("Root"))
      })
    }

    val splitter = new SplitPane {
      orientation = Orientation.Vertical
      leftComponent = treeTab
      rightComponent = tabs
      enabled = true
      resizeWeight = 0.30
    }

    val statusBar = new StatusBar {
      statusMessage = "Foo"
      progressBar.indeterminate = true
      progressBar.visible = true
    }

    contents = new BorderPanel {
      layout(toolBar) = North
      layout(splitter) = Center
      layout(statusBar) = South
    }

    location = windowPrefs.point()
    size = windowPrefs.size
  }

}