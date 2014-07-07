/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.client.pc.view;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import iquiz.client.controller.ClientController;
import iquiz.main.model.Helper;
import iquiz.main.model.Logging;
import iquiz.main.model.game.Game;
import iquiz.main.model.game.Player;
import iquiz.main.model.game.question.BasicQuestion;
import iquiz.main.model.game.question.BasicSolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by philipp on 13.06.14.
 */
public class DuelView extends ShowableView {

    private Game relatedGame;

    protected final int sizeX = 1;
    protected final int sizeY = 1;

    private final QuestionWindowListener questionViewWindowListener = new QuestionWindowListener();

    private JPanel pnlDuel;
    private JLabel lblHeader;
    private JLabel lblOwnName;
    private JLabel lblOpponentName;
    private JPanel pnlOwn;
    private JPanel pnlOpponent;
    private JLabel lblScore;
    private JPanel pnlInformation;

    public DuelView(Game game) {
        this.frameSize = new Dimension(sizeX, sizeY);

        this.frame = new JFrame("Duel: " + game.getOpponent(ClientController.getInstance().getConnection().getRelatedPlayer()).getNickname());
        this.relatedGame = game;

        this.initializeFrame();
        this.applyPanel(this.pnlDuel);
    }

    private void initializeFrame() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        frame.setLocation((int) (centerPoint.getX() - (sizeX / 2)), (int) (centerPoint.getY() - (sizeY / 2)));
        frame.setSize(this.frameSize);
        frame.setMinimumSize(this.frameSize);
        frame.setResizable(true);
        frame.setVisible(true);

        this.paintInformation();
    }

    private void paintInformation() {
        final Player ownPlayer = ClientController.getInstance().getConnection().getRelatedPlayer();
        Player opponentPlayer = relatedGame.getOpponent(ownPlayer);

        lblOwnName.setText(ownPlayer.getNickname());
        lblOpponentName.setText(opponentPlayer.getNickname());

        FormLayout ownGamesLayout = new FormLayout("center:pref, 3dlu", "");
        DefaultFormBuilder ownGamesFormBuilder = new DefaultFormBuilder(ownGamesLayout, pnlOwn);

        FormLayout opponentGamesLayout = new FormLayout("center:pref, 3dlu", "");
        DefaultFormBuilder opponentGamesFormBuilder = new DefaultFormBuilder(opponentGamesLayout, pnlOpponent);

        FormLayout informationGamesLayout = new FormLayout("center:pref:grow, 3dlu", "");
        DefaultFormBuilder informationGamesFormBuilder = new DefaultFormBuilder(informationGamesLayout, pnlInformation);

        int scoreOwn = 0;
        int scoreOpponent = 0;

        for(final BasicQuestion question : relatedGame.getQuestions()){
            BasicSolution solutionOwn = question.getChosenAnswers().get(ownPlayer);
            BasicSolution solutionOpponent = question.getChosenAnswers().get(opponentPlayer);

            String textOwn = solutionOwn == null ? "Not answered yet" : solutionOwn.getText();
            JLabel lblOwn = new JLabel(textOwn);
            if(solutionOwn != null){
                Color colorOwn;
                if(solutionOwn.isCorrect()){
                    scoreOwn++;
                    colorOwn = Color.GREEN;
                }else{
                    colorOwn = Color.RED;
                }
                lblOwn.setForeground(colorOwn);
            }

            String textOpponent = solutionOpponent == null ? "Not answered yet" : (solutionOwn == null ? "Hidden" : solutionOpponent.getText());
            JLabel lblOpponent = new JLabel(textOpponent);
            if(solutionOpponent != null){
                Color colorOpponent;
                if(solutionOwn == null){
                    colorOpponent = Color.BLACK;
                }else if(solutionOpponent.isCorrect()){
                    scoreOpponent++;
                    colorOpponent = Color.GREEN;
                }else{
                    colorOpponent = Color.RED;
                }
                lblOpponent.setForeground(colorOpponent);
            }

            if(solutionOwn == null){
                JLabel playLabel = new JLabel("Play!");
                playLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e){
                        QuestionView qv = new QuestionView(ownPlayer, relatedGame, question);
                        DuelView.this.frame.setVisible(false);
                        qv.frame.addWindowListener(questionViewWindowListener);
                    }
                });
                informationGamesFormBuilder.append(playLabel);
                informationGamesFormBuilder.nextLine();
            }else{
                JLabel lbl = new JLabel(question.getCategory().toCapitalizedString());
                lbl.setToolTipText(question.getQuestionText());
                informationGamesFormBuilder.append(lbl);
                informationGamesFormBuilder.nextLine();
            }

            ownGamesFormBuilder.append(lblOwn);
            ownGamesFormBuilder.nextLine();

            opponentGamesFormBuilder.append(lblOpponent);
            opponentGamesFormBuilder.nextLine();
        }

        lblScore.setText(scoreOwn + " - " + scoreOpponent);
    }

    public static void openFor(Game game) {
        new DuelView(game);
    }

    private class QuestionWindowListener implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            DuelView.this.pnlDuel.removeAll();
            DuelView.this.pnlInformation.removeAll();
            DuelView.this.pnlOpponent.removeAll();
            DuelView.this.paintInformation();
            DuelView.this.frame.setVisible(true);
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

}
