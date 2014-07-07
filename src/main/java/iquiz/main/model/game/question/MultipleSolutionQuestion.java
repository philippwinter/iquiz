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
public abstract class MultipleSolutionQuestion<E> extends BasicQuestion implements Cloneable {

    public void setSolutions(ArrayList<E> solutions) {
        this.solutions = solutions;
    }

    protected ArrayList<E> solutions;

    protected MultipleSolutionQuestion(Category category) {
        super(category);
    }

}
