/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game;

import iquiz.main.model.game.question.BasicQuestion;
import iquiz.main.model.game.question.BasicSolution;

import java.io.Serializable;

/**
 * Created by philipp on 08.05.14.
 */
public class Statistic implements Serializable {

    public static String buildMessage(Player player) {
        StringBuilder sb = new StringBuilder();

        int gamesCount = player.getGames().size();
        int ownTotalScore = 0;

        for(Game g : player.getGames()){
            for(BasicQuestion q : g.getQuestions()){
                if(q != null){
                    BasicSolution s = q.getChosenAnswers().get(player);
                    if(s != null && s.isCorrect()){
                        ownTotalScore++;
                    }
                }
            }
        }

        sb.append("The player ").append(player.getNickname()).append(" has played ").append(gamesCount).append(" games with a total score of ").append(ownTotalScore).append(" which equals a ratio of ").append(ownTotalScore / gamesCount).append(" points out of ").append(Game.QUESTIONS_PER_GAME).append(" questions!");

        return sb.toString();
    }
}
