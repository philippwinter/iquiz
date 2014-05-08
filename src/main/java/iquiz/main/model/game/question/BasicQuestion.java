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

import java.util.Date;

/**
 *
 * @author HTS1205U13
 */
public abstract class BasicQuestion {

    private String questionText;

    private Date timeAsked;

    private Category category;

    private BasicSolution solutionChosen;

    public BasicQuestion factory(Category category) {
        switch((int) (Math.random() * 3)){
            case 0: return new EstimationQuestion(category);
            case 1: return new MultipleChoiceQuestion(category);
            case 2: return new PictureQuestion(category);
        }

        throw new RuntimeException("ERROR: Could not construct any question :( doh");
    }

    protected BasicQuestion(Category category){
        this.selectQuestion();
        this.category = category;
    }

    protected abstract void selectQuestion();

    public Category getCategory() {
        return category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Date getTimeAsked() {
        return timeAsked;
    }

    public BasicSolution getSolutionChosen() {
        return solutionChosen;
    }

    public void setSolutionChosen(BasicSolution solutionChosen) {
        this.solutionChosen = solutionChosen;
    }

    protected void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
