/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iquiz.client.controller;

import iquiz.client.model.network.ServerConnection;
import iquiz.client.pc.view.MainView;
import iquiz.main.controller.BasicController;
import iquiz.main.controller.MainController;
import iquiz.main.model.Logging;
import iquiz.main.model.game.Language;
import iquiz.main.model.game.Player;

import javax.swing.*;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author HTS1205U13
 */
public class ClientController implements BasicController {

    public static final int PORT = 1234;

    private static ClientController instance;

    private MainView mainView;

    public ServerConnection getConnection() {
        return connection;
    }

    private ServerConnection connection;

    public static ClientController getInstance() {
        if(instance == null){
           instance = new ClientController();
        }
        return instance;
    }

    private ClientController() {
        this.connection = new ServerConnection();
        this.connection.start();
    }

    @Override
    public void initialize() {
        switch(MainController.OS){
            case UNKNOWN:
            case WINDOWS:
            case MAC_OS_X:
                MainView.create();
                this.mainView = MainView.getInstance();
                break;
            case ANDROID:
                // TODO: Initialize android application
                break;
            default:
                throw new RuntimeException("Could not determine view suitable for OS");
        }
    }

    public void showRegistration() {
        this.mainView.showRegistrationView();
    }

    public void showLogin() {
        this.mainView.showLoginView();
    }

    public void showGame() { this.mainView.showGameView(); }

    public void login(String username, String password) {
        this.connection.login(username, password);
    }

    public void loginCallback(boolean success){
        if(success){
            try {
                ClientController.getInstance().getConnection().doPing();
                switch(MainController.OS){
                    case UNKNOWN:
                    case WINDOWS:
                    case MAC_OS_X:
                        this.showGame();
                        break;
                    case ANDROID:
                    default:
                        throw new RuntimeException("Cannot do anything weil problem");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(MainView.getInstance().getFrame(),
                    "Your login was unsuccessful!",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void register(String name, String username, String password, String email, Player.Sex sex, Date date, Language language) {
        this.connection.register(name, username, password, email, sex, date, language);
    }

    public void registrationCallback(boolean success){
        if(success){
            try {
                ClientController.getInstance().getConnection().doPing();
                switch(MainController.OS){
                    case UNKNOWN:
                    case WINDOWS:
                    case MAC_OS_X:
                        this.showGame();
                        break;
                    case ANDROID:
                    default:
                        throw new RuntimeException("Cannot do anything weil problem");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(MainView.getInstance().getFrame(),
                    "Your registration was unsuccessful!",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshData() {
        this.connection.doDataRefresh();
    }

    public void showDuel(Player contrahent) {
        Logging.log(Logging.Priority.ERROR, "Not implemented yet: Duel against", contrahent);
    }

    public void requestForGame(String opponent) {
        Logging.log(Logging.Priority.ERROR, "Not implemented yet: Request game against", opponent);
    }
}
