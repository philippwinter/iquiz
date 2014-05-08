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
 * Created by philipp on 08.05.14.
 */
public class MainView {

    private static MainView instance;


    private JFrame frame;
    private JPanel loginPanel;
    private JTextField txtPassword;
    private JTextField txtUsername;
    private JButton btnLogin;
    private JButton btnSignup;
    private JLabel lblPassword;
    private JLabel lblUsername;
    private JLabel lblHeader;


    private MainView() {
        this.frame = new JFrame("iQuiz");
        frame.setContentPane(this.loginPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        final int sizeX = 400;
        final int sizeY = 350;
        frame.setLocation((int) (centerPoint.getX() - (sizeX / 2)), (int) (centerPoint.getY() - (sizeY / 2)));
        Dimension d = new Dimension(sizeX, sizeY);
        frame.setSize(d);
        frame.setMinimumSize(d);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void create() {
        if(instance != null){
            throw new RuntimeException("There must not be more than one MainView at a time");
        }
        MainView.instance = new MainView();
    }

    public static MainView getInstance() {
        return instance;
    }
}
