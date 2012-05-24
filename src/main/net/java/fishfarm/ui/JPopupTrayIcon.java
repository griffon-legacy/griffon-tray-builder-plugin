/*
* Created on Sep 15, 2008  5:51:33 PM
*/

package net.java.fishfarm.ui;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static griffon.util.GriffonApplicationUtils.isMacOSX;
import static griffon.util.GriffonApplicationUtils.isWindows;


/**
 * JPopupMenu compatible TrayIcon based on Alexander Potochkin's JXTrayIcon
 * (http://weblogs.java.net/blog/alexfromsun/archive/2008/02/jtrayicon_updat.html)
 * but uses a JWindow instead of a JDialog to workaround some bugs on linux.
 *
 * @author Michael Bien
 */
public class JPopupTrayIcon extends TrayIcon {
    private JPopupMenu menu;

    private Window window;
    private PopupMenuListener popupListener;

    public JPopupTrayIcon(Image image) {
        super(image);
        init();
    }

    public JPopupTrayIcon(Image image, String tooltip) {
        super(image, tooltip);
        init();
    }

    public JPopupTrayIcon(Image image, String tooltip, PopupMenu popup) {
        super(image, tooltip, popup);
        init();
    }

    public JPopupTrayIcon(Image image, String tooltip, JPopupMenu popup) {
        super(image, tooltip);
        init();
        setJPopupMenu(popup);
    }


    private final void init() {
        popupListener = new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (window != null) {
                    window.dispose();
                    window = null;
                }
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
                if (window != null) {
                    window.dispose();
                    window = null;
                }
            }
        };

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showJPopupMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showJPopupMenu(e);
            }
        });

    }

    private void showJPopupMenu(MouseEvent e) {
        if (e.isPopupTrigger() && menu != null) {
            if (window == null) {

                if (isWindows()) {
                    window = new JDialog((Frame) null);
                    ((JDialog) window).setUndecorated(true);
                } else {
                    window = new JWindow((Frame) null);
                }
                window.setAlwaysOnTop(true);
                Dimension size = menu.getPreferredSize();

                Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();

                // Both e.getX() and e.getY() return values relative to
                // e.getSource(), however in OSX these values are not translated
                // to the correct location, thus we have to figure out the right
                // locations in OSX. Sadly there's no way to figure out the X
                // location of trayIcon thus the value for x is not aligned with
                // the icon's x location :-(

                int x = isMacOSX() ? e.getXOnScreen() : e.getX();
                int y = isMacOSX() ? getSize().height : e.getY();

                if (y > centerPoint.getY()) {
                    window.setLocation(x, y - size.height);
                } else {
                    window.setLocation(x, y);
                }

                window.setVisible(true);

                menu.show(((RootPaneContainer) window).getContentPane(), 0, 0);

                // popup works only for focused windows
                window.toFront();
            }
        }
    }

    public final JPopupMenu getJPopupMenu() {
        return menu;
    }

    public final void setJPopupMenu(JPopupMenu menu) {
        if (this.menu != null) {
            this.menu.removePopupMenuListener(popupListener);
        }
        this.menu = menu;
        menu.addPopupMenuListener(popupListener);
    }
}
