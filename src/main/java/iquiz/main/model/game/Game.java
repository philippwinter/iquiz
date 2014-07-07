/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game;

import iquiz.main.model.game.question.BasicQuestion;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by philipp on 08.05.14.
 */
public class Game implements Serializable {

    public final static int QUESTIONS_PER_GAME = 12;

    private Player challenger;
    private Player challengee;

    private ArrayList<BasicQuestion> questions;

    public static Game factory(Player playerOne, Player playerTwo) {
        Game game = new Game(playerOne, playerTwo);


            playerOne.getGames().add(game);
            playerTwo.getGames().add(game);

        return game;
    }

    public Game(Player challenger, Player challengee) {
        this.challenger = challenger;
        this.challengee = challengee;

        this.questions = new ArrayList<>(QUESTIONS_PER_GAME);

        for(int i = 0; i < QUESTIONS_PER_GAME; i++){
            this.questions.add(BasicQuestion.factory());
        }

    }

    public Player getOpponent(Player forPlayer){
        return forPlayer.equals(challenger) ? challengee : challenger;
    }

    public ArrayList<BasicQuestion> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);

        sb.append("Challenger: ");
        sb.append(challenger.getUsername());
        sb.append(";\n");

        sb.append("Challangee: ");
        sb.append(challengee.getUsername());
        sb.append(";\n");

        sb.append("Questions: ");
        sb.append(questions);
        sb.append(";\n");

        return sb.toString();
    }

    public String getShortDescription() {
        return challengee.getUsername() + " vs. " + challenger.getUsername();
    }
}
