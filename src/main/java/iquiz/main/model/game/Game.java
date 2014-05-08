/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game;

import iquiz.main.model.game.question.BasicQuestion;

import java.util.ArrayList;

/**
 * Created by philipp on 08.05.14.
 */
public class Game {

    private Player challenger;

    private Player challengee;

    private ArrayList<BasicQuestion> questions;
}
