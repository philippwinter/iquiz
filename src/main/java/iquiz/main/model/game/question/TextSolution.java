/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game.question;

/**
 * Created by philipp on 08.05.14.
 */
public class TextSolution extends BasicSolution {

    private String answerText;

    public TextSolution(String answerText, boolean isCorrect){
        super(isCorrect);
        this.setAnswerText(answerText);
    }

    public String getAnswerText() {
        return answerText;
    }

    protected void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
