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
import iquiz.main.model.game.Player;
import iquiz.main.model.game.question.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by philipp on 01.07.14.
 */
public class QuestionView extends ShowableView {

    protected final int sizeX = 1;
    protected final int sizeY = 1;

    private final Player player;
    private final BasicQuestion question;
    private JPanel pnlQuestion;
    private JPanel pnlMultipleChoice;
    private JButton btnTopLeft;
    private JButton btnTopRight;
    private JButton btnBottomLeft;
    private JButton btnBottomRight;
    private JLabel lblQuestionTextMultipleChoice;
    private JPanel pnlEstimation;
    private JTextField txtEstimation;
    private JButton btnSubmitEstimation;
    private JLabel lblQuestionTextEstimation;
    private JProgressBar progressTimeLeft;

    private Timer timer;

    public QuestionView(Player player, BasicQuestion question){
        this.player = player;
        this.question = question;

        this.frameSize = new Dimension(sizeX, sizeY);
        this.frame = new JFrame("Play!");

        this.initializeFrame();
        this.initializeComponents();

        this.applyPanel(pnlQuestion);
        this.frame.pack();
        this.startTimer();
    }

    private void startTimer() {
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressTimeLeft.setValue(progressTimeLeft.getValue() - 1);
                if(progressTimeLeft.getValue() == progressTimeLeft.getMinimum()){
                    QuestionView.this.outOfTime();
                    timer.stop();
                }
            }
        });
        this.timer.start();
    }

    private void deactivateButtons() {
        this.btnSubmitEstimation.setEnabled(false);
        this.btnTopLeft.setEnabled(false);
        this.btnTopRight.setEnabled(false);
        this.btnBottomLeft.setEnabled(false);
        this.btnBottomRight.setEnabled(false);
    }

    private void outOfTime() {
        this.deactivateButtons();

        this.btnSubmitEstimation.setBackground(Color.RED);
        this.btnTopLeft.setBackground(Color.RED);
        this.btnTopRight.setBackground(Color.RED);
        this.btnBottomLeft.setBackground(Color.RED);
        this.btnBottomRight.setBackground(Color.RED);

        this.question.getChosenAnswers().put(this.player, new IllegalSolution());
        ClientController.getInstance().pushIllegalSolution(question);
        this.frame.dispose();
    }

    private void initializeFrame() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        frame.setLocation((int) (centerPoint.getX() - (sizeX / 2)), (int) (centerPoint.getY() - (sizeY / 2)));
        frame.setSize(this.frameSize);
        frame.setMinimumSize(this.frameSize);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    private void initializeComponents() {
        if(question instanceof MultipleChoiceQuestion){
            lblQuestionTextMultipleChoice.setText(question.getQuestionText());

            btnTopLeft.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chooseMultipleChoice(question.getSolutions().get(0), btnTopLeft);
                    timer.stop();
                }
            });
            btnTopLeft.setText(question.getSolutions().get(0).getText());

            btnTopRight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chooseMultipleChoice(question.getSolutions().get(1), btnTopRight);
                    timer.stop();
                }
            });
            btnTopRight.setText(question.getSolutions().get(1).getText());

            btnBottomLeft.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chooseMultipleChoice(question.getSolutions().get(2), btnBottomLeft);
                    timer.stop();
                }
            });
            btnBottomLeft.setText(question.getSolutions().get(2).getText());

            btnBottomRight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chooseMultipleChoice(question.getSolutions().get(3), btnBottomRight);
                    timer.stop();
                }
            });
            btnBottomRight.setText(question.getSolutions().get(3).getText());

            pnlMultipleChoice.setVisible(true);

        }else if(question instanceof EstimationQuestion){
            EstimationQuestion esQuestion = (EstimationQuestion) question;
            lblQuestionTextEstimation.setText(esQuestion.getQuestionText());

            btnSubmitEstimation.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chooseEstimation(Double.parseDouble(txtEstimation.getText()));
                    timer.stop();
                }
            });
            pnlEstimation.setVisible(true);
        }
        this.frame.revalidate();
        this.frame.repaint();
    }

    private void chooseMultipleChoice(BasicSolution solution, JButton button) {
        this.deactivateButtons();

        if(solution.isCorrect()){
            button.setBackground(Color.RED);
        }else{
            button.setBackground(Color.GREEN);
        }

        Logging.log(Logging.Priority.MESSAGE, "Chose", solution);
        ClientController.getInstance().pushSolution(question, solution);
        this.frame.dispose();
    }

    private void chooseEstimation(Double i){
        this.deactivateButtons();

        Logging.log(Logging.Priority.MESSAGE, "Chose", i);
        ClientController.getInstance().pushSolution(question, i);
        this.frame.dispose();
    }
}
