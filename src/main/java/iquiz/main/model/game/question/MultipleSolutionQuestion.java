/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game.question;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by philipp on 08.05.14.
 */
public abstract class MultipleSolutionQuestion extends BasicQuestion {

    public ArrayList<BasicSolution> getSolutions() {
        return solutions;
    }

    private ArrayList<BasicSolution> solutions;

    protected MultipleSolutionQuestion(Category category) {
        super(category);
    }

    @Override
    protected void selectQuestion() {
        this.setQuestionText("2 Kokosnüsse hängen an der Palme. Jan schüttelt kräftig daran. Wie viele fallen runter?");

        solutions.add(new TextSolution("Alle", true));
        solutions.add(new TextSolution("1", false));
        solutions.add(new TextSolution("Keine", false));
        solutions.add(new TextSolution("2", false));

        Collections.shuffle(this.solutions);
    }
}
