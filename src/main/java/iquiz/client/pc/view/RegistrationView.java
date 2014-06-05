/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.client.pc.view;

import com.toedter.calendar.JDateChooser;
import iquiz.client.controller.ClientController;
import iquiz.main.controller.MainController;
import iquiz.main.model.game.Language;
import iquiz.main.model.game.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by philipp on 09.05.14.
 */
public class RegistrationView {

    private JPanel pnlRegistration;
    private JLabel lblHeader;
    private JTextField txtName;
    private JTextField txtUsername;
    private JPasswordField pwdPassword;
    private JTextField txtEmail;
    private JComboBox cbxSex;
    private JComboBox cbxLanguage;
    private JButton btnRegister;
    private JButton btnLogin;
    private JDateChooser pckBirthday;

    public RegistrationView() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientController.getInstance().showLogin();
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String username = txtUsername.getText();
                String password = new String(pwdPassword.getPassword());
                String email = txtEmail.getText();
                Date date = pckBirthday.getDate();
                Player.Sex sex;
                switch(cbxSex.getSelectedIndex()){
                    case 0:
                        sex = Player.Sex.MALE;
                        break;
                    case 1:
                        sex = Player.Sex.FEMALE;
                        break;
                    default:
                        sex = Player.Sex.UNKNOWN;
                }
                Language language = Language.valueOf(cbxLanguage.getSelectedItem().toString().toUpperCase());
                ClientController.getInstance().register(name, username, password, email, sex, date, language);
            }
        });
    }

    public JPanel getPnlRegistration() {
        return pnlRegistration;
    }
}
