/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.client.pc.view;

import iquiz.client.controller.ClientController;
import iquiz.main.model.game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by philipp on 09.05.14.
 */
public class MainView extends ShowableView {

    private static MainView instance;

    public JFrame getFrame() {
        return frame;
    }

    protected final int sizeX = 500;
    protected final int sizeY = 600;

    private LoginView loginView;
    private GameView gameView;
    private RegistrationView registrationView;

    private MainView() {
        this.frameSize = new Dimension(sizeX, sizeY);

        this.registrationView = new RegistrationView();
        this.loginView = new LoginView();
        this.gameView = new GameView();
        this.frame = new JFrame("iQuiz");
        this.initializeFrame();
    }

    private void initializeFrame() {
        this.showLoginView();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        frame.setLocation((int) (centerPoint.getX() - (sizeX / 2)), (int) (centerPoint.getY() - (sizeY / 2)));
        frame.setSize(this.frameSize);
        frame.setMinimumSize(this.frameSize);
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

    public void showRegistrationView() {
        this.applyPanel(this.registrationView.getPnlRegistration());
    }

    public void showLoginView() {
        this.applyPanel(this.loginView.getPnlLogin());
    }

    public void showGameView() {
        this.applyPanel(this.gameView.getPnlGame());
        ClientController.getInstance().pullData();
        this.gameView.refresh();
    }

    public void showGame(Game game) {
        DuelView.openFor(game);
    }

    public GameView getGameView() {
        return gameView;
    }
}
