/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.client.pc.view;

import iquiz.client.controller.ClientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by philipp on 08.05.14.
 */
public class LoginView {

    private JPanel pnlLogin;
    private JTextField txtPassword;
    private JTextField txtUsername;
    private JButton btnLogin;
    private JButton btnSignup;
    private JLabel lblPassword;
    private JLabel lblUsername;
    private JLabel lblHeader;

    public LoginView() {
        btnSignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientController.getInstance().showRegistration();
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                ClientController.getInstance().login(username, password);
            }
        });
    }

    public JPanel getPnlLogin() {
        return pnlLogin;
    }
}
