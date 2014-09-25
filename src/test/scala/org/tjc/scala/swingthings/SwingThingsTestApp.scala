package org.tjc.scala.swingthings

import scala.swing.Action
import scala.swing.BorderPanel
import scala.swing.BorderPanel.Position.Center
import scala.swing.BorderPanel.Position.North
import scala.swing.BorderPanel.Position.South
import scala.swing.Button
import scala.swing.Font
import scala.swing.Insets
import scala.swing.Menu
import scala.swing.MenuBar
import scala.swing.MenuItem
import scala.swing.Orientation
import scala.swing.ScrollPane
import scala.swing.Separator
import scala.swing.SimpleSwingApplication
import scala.swing.SplitPane
import scala.swing.TabbedPane
import scala.swing.TabbedPane.Page
import scala.swing.TextArea
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger
import SwingThings.Nimbus
import SwingThings.installedLookAndFeelNames
import SwingThings.setLookAndFeelWithName
import ToolBar.toolbarButton
import org.tjc.scala.swingthings.tree.TreeNode
import org.tjc.scala.swingthings.event.TreeExpanded
import org.tjc.scala.swingthings.event.TreeCollapsed
import scala.swing.event.MouseClicked
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased
import org.tjc.scala.swingthings.event.TreeSelection
import javax.swing.SwingUtilities

object SwingThingsTestApp extends SimpleSwingApplication {
  import SwingThings._
  private val logger = Logger(LoggerFactory.getLogger(getClass()))

  private val appTitle = "SwingThings Test App"

  setLookAndFeelWithName(Nimbus)

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
      contents += toolbarButton("open_document_24.png", Action("") {}, "Open a file")
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
        case TreeExpanded(tree, treePath)  => logger.debug(s"treeExpanded: treePath=$treePath, tree=$tree")
        case TreeCollapsed(tree, treePath) => logger.debug(s"treeCollapsed: treePath=$treePath, tree=$tree")
        case TreeSelection(_, newSelPath, oldSelPath, treePath, _, isAdded) => logger.debug(s"treeSelection: $newSelPath, $oldSelPath, $treePath, $isAdded")
//        case mc:MouseClicked => {
//          if(SwingUtilities.isLeftMouseButton(mc.peer)) println("left mouse button clicked")
//          if(SwingUtilities.isRightMouseButton(mc.peer)) println("right mouse buttong clicked")
//          if(SwingUtilities.isMiddleMouseButton(mc.peer)) println("middle mouse buttong clicked")
//        }
        case MouseClicked(source, point, mods, clicks, triggersPopup) => {
          println(s"self: $self")
          logger.debug(s"got mouse clicked at point=$point, mods=$mods, clicks=$clicks, triggersPopup=$triggersPopup, source=$source")          
        }
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