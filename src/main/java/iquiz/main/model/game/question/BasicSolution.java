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

import java.io.Serializable;

/**
 *
 * @author HTS1205U13
 */
public abstract class BasicSolution implements Serializable {

    private boolean isCorrect;

    protected String text;

    public BasicSolution(String text, boolean isCorrect) {
        this.isCorrect = isCorrect;
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BasicSolution{");
        sb.append("isCorrect=").append(isCorrect);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
