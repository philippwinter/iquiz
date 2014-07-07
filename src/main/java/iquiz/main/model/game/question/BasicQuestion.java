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
package iquiz.main.model.game.question;

import iquiz.main.model.game.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author HTS1205U13
 */
public abstract class BasicQuestion implements Serializable, Cloneable{

    private String questionText;

    protected Category category;

    protected ArrayList<BasicSolution> solutions = new ArrayList<>();

    private HashMap<Player, BasicSolution> chosenAnswers = new HashMap<>(2);

    public static BasicQuestion factory() {
        return QuestionPool.getInstance().getRandom();
    }

    protected BasicQuestion(Category category){
        this.category = category;
    }

    public ArrayList<BasicSolution> getSolutions() {
        return solutions;
    }

    public Category getCategory() {
        return category;
    }

    public String getQuestionText() {
        return questionText;
    }

    protected void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public HashMap<Player, BasicSolution> getChosenAnswers() {
        return chosenAnswers;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BasicQuestion{");
        sb.append("questionText='").append(questionText).append('\'');
        sb.append(", category=").append(category);
        sb.append(", solutions=").append(solutions);
        sb.append(", chosenAnswers=").append(chosenAnswers);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof BasicQuestion){
            BasicQuestion bq = (BasicQuestion) o;
            return bq.category == this.category && bq.questionText.equals(this.questionText);
        }
        return false;
    }
}
