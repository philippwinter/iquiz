/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.client.pc.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by philipp on 14.06.14.
 */
public abstract class ShowableView {

    protected JFrame frame;
    protected Dimension frameSize;

    protected void applyPanel(JPanel panel) {
        panel.setVisible(false);
        frame.setContentPane(panel);
        panel.setSize(frameSize);
        panel.setMinimumSize(frameSize);
        frame.repaint();
        panel.setVisible(true);
        frame.pack();
        frame.setResizable(false);
    }


}
