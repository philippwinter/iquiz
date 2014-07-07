/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.client.pc.view;

import iquiz.client.controller.ClientController;
import iquiz.main.model.Logging;
import iquiz.main.model.game.Game;
import iquiz.main.model.game.Player;
import iquiz.main.model.game.Statistic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by philipp on 20.05.14.
 */
public class GameView {
    private JPanel pnlGame;
    private JLabel lblHeader;
    private JButton btnStartNewGame;
    private JButton btnStatistics;
    private JPanel pnlIconButtons;
    private JButton refreshButton;
    private JPanel pnlRunningGames;

    public void refresh() {
        Logging.log(Logging.Priority.MESSAGE, "Repainting data in game view. Data must have been fetched before manually!");

        this.initializeRunningGamesPanel();
    }

    private void initializeRunningGamesPanel(){
        initializeRunningGamesPanel(ClientController.getInstance().getConnection().getRelatedPlayer());
    }

    private void initializeRunningGamesPanel(final Player player) {
        JPanel panel = new JPanel(new FlowLayout());

        Vector<Game> runningGames = player.getGames();
        for(final Game game : runningGames){
            JButton button = new JButton(game.getOpponent(player).getNickname());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClientController.getInstance().showDuel(game);
                }
            });

            panel.add(button);

            Logging.log(Logging.Priority.MESSAGE, "Created button for game", game.getShortDescription());
        }

        this.pnlRunningGames.removeAll();
        this.pnlRunningGames.add(panel);
        this.pnlRunningGames.revalidate();
        this.pnlRunningGames.repaint();
    }

    public GameView() {
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientController.getInstance().pullData();
                initializeRunningGamesPanel();
            }
        });
        btnStartNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enemyUsername =  JOptionPane.showInputDialog(MainView.getInstance().getFrame(), "Enter the username of your opponent:", "");
                ClientController.getInstance().requestForGame(enemyUsername);
            }
        });
        btnStatistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainView.getInstance().getFrame(), Statistic.buildMessage(ClientController.getInstance().getConnection().getRelatedPlayer()), "Statistics", JOptionPane.PLAIN_MESSAGE, null);
            }
        });
    }

    public JPanel getPnlGame() {
        return pnlGame;
    }

    public JPanel getPnlRunningGames() { return pnlRunningGames; }
}
