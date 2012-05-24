/* --------------------------------------------------------------------
   TrayBuilder
   Copyright (C) 2008-2012 Andres Almiray

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU General Public License as
   published by the Free Software Foundation; either version 2 of the
   License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this library; if not, see <http://www.gnu.org/licenses/>.
   ---------------------------------------------------------------------
*/

/**
 * @author Andres Almiray
 */
class TrayBuilderGriffonPlugin {
    String version = '0.8'
    // the version or versions of Griffon the plugin is designed for
    String griffonVersion = '0.9.5 > *'
    // the other plugins this plugin depends on
    Map dependsOn = [swing: '0.9.5']
    // resources that are included in plugin packaging
    List pluginIncludes = []
    // the plugin license
    String license = 'GNU General Public License 2.0 (with classpath extension)'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, gtk
    List toolkits = ['swing']
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    List platforms = []
    // URL where documentation can be found
    String documentation = ''
    // URL where source can be found
    String source = 'https://github.com/griffon/griffon-tray-builder-plugin'

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ]
    ]
    String title = 'SystemTray component'
    // accepts Markdown syntax. See http://daringfireball.net/projects/markdown/ for details
    String description = '''
Enables `SystemTray` and `TrayIcon` support on your Griffon application.

The TrayIcon class is actually `JPopupTrayIcon` by Michael Biem (from the Fishfarm project), which in turn is based on
Alex Potochkin's `JXTrayIcon`. In short words this class allows you to display a `JPopupMenu` in the systemTray as opposed
to a regular, plain `PopupMenu` (AWT stuff, yuck!)

Usage
-----

There is no standalone builder as opposed to the other builder plugins, as this one only adds 2 node factories and relies on
Griffon's CompositeBuilder to be mixed&amp;matched with other [SwingBuilder based builders][1], those factories are

| Node       | Property     | Type        | Required | Bindable | Notes                                                                                    |
| ---------- | ------------ | ----------- | -------- | -------- | ---------------------------------------------------------------------------------------- |
| systemTray | trayIcons    | TrayIcon    | no       | no       | readonly                                                                                 |
|            | trayIconSize | Dimension   | no       | no       | readonly                                                                                 |
| trayIcon   | url          | URL         | yes      | no       |                                                                                          |
|            | file         | File        | yes      | no       | can also be a `String`                                                                   |
|            | inputStream  | InputStream | yes      | no       |                                                                                          |
|            | resource     | String      | yes      | no       |                                                                                          |
|            | class        | Class       | yes      | no       | only required if `resource` is specified.                                                |
|            |              |             |          |          | specify one of `url`, `file`, `inputStream`, `resource` or use a `String` as node value. |
|            |              |             |          |          | accepts `JPopupMenu` instances as children only.                                         |

The following is an example of its usage provided by SwingPad as sample script:

        import groovy.ui.Console
        import static java.awt.TrayIcon.MessageType.*
 
        actions {
           action(id: 'cutAction',
              name: 'Cut',
              closure: { trayIcon.displayMessage("Cut","Cut some content",NONE)},
              mnemonic: 'T',
              accelerator: shortcut('X'),
              smallIcon: imageIcon(resource:"icons/cut.png", class: Console),
              shortDescription: 'Cut'
           )
           action(id: 'copyAction',
              name: 'Copy',
              closure: { trayIcon.displayMessage("Copy","Copy some content",INFO)},
              mnemonic: 'C',
              accelerator: shortcut('C'),
              smallIcon: imageIcon(resource:"icons/page_copy.png", class: Console),
              shortDescription: 'Copy'
           )
           action(id: 'pasteAction',
              name: 'Paste',
              closure: { trayIcon.displayMessage("Paste","Paste some content",WARNING)},
              mnemonic: 'P',
              accelerator: shortcut('V'),
              smallIcon: imageIcon(resource:"icons/page_paste.png", class: Console),
              shortDescription: 'Paste'
           )
           action( id: 'exitAction',
              name: 'Quit',
              closure: { trayIcon.displayMessage("Quit","No can do!",ERROR)},
              mnemonic: 'Q',
              accelerator: shortcut('Q'),
              shortDescription: 'Quit'
           )
        }
 
        systemTray {
          trayIcon(id: "trayIcon",
            resource: "/groovy/ui/ConsoleIcon.png",
            class: groovy.ui.Console,
            toolTip: "Double click on me and I'll go away!",
            actionPerformed: {systemTray.remove(trayIcon)}) {
            popupMenu {
               menuItem(cutAction)
               menuItem(copyAction)
               menuItem(pasteAction)
               separator()
               menuItem(exitAction)
            }
          }
        }

A variable `systemTray` is available for your convenience, it points to the `SystemTray` instance.

[1]: http://griffon.codehaus.org/Builders
'''
}
