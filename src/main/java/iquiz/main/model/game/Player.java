/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game;

import java.util.ArrayList;

/**
 * Created by philipp on 08.05.14.
 */
public class Player {

    private long id;

    private String username;

    private String passwordHash;

    private Statistic statistic;

    private ArrayList<Game> lastGames;

}
