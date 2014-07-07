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


/**
 *
 * @author HTS1205U13
 */
public class MultipleChoiceQuestion extends MultipleSolutionQuestion<TextSolution> implements Cloneable {

    protected MultipleChoiceQuestion(Category category) {
        super(category);
    }

    @Override
    public MultipleChoiceQuestion clone() throws CloneNotSupportedException {
        super.clone();
        MultipleChoiceQuestion mc = new MultipleChoiceQuestion(this.category);
        mc.setQuestionText(this.getQuestionText());
        for(BasicSolution s : this.getSolutions()){
            mc.getSolutions().add(new TextSolution(s.getText(), s.isCorrect()));
        }

        return mc;
    }
}
