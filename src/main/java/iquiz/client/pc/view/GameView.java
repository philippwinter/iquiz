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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton settingsButton;
    private JPanel pnlRunningGames;
    private JLabel lblRunningGames;

    public void refresh() {
        this.pnlRunningGames.removeAll();
        this.pnlRunningGames.add(this.lblRunningGames);

        Player player = ClientController.getInstance().getConnection().getCurrentPlayer();

        Logging.log(Logging.Priority.MESSAGE, "Refreshing data in game view.", player);

        for(Game game : player.getRunningGames()){
            //final DataEnabledButton<Player> button = new DataEnabledButton<>(game.getContrahent(player).getName(), game.getContrahent(player));

            final Player contrahent = game.getContrahent(player);
            JButton button = new JButton(contrahent.getName());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClientController.getInstance().showDuel(contrahent);
                }
            });
        }


    }

    public GameView() {
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientController.getInstance().refreshData();
            }
        });
        btnStartNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Open dialog to ask for the username and dispatch it to the controller
                String enemyUsername =  JOptionPane.showInputDialog(MainView.getInstance().getFrame(), "Enter the username of your opponent:", "");
                ClientController.getInstance().requestForGame(enemyUsername);
            }
        });
    }

    public JPanel getPnlGame() {
        return pnlGame;
    }

    public JPanel getPnlRunningGames() { return pnlRunningGames; }

    private class DataEnabledButton<T> extends JButton {
        final T data;

        public DataEnabledButton(String text, T data){
            this.data = data;
        }
    }

}
