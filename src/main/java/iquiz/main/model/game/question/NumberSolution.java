/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game.question;

import iquiz.main.model.game.Player;
import java.util.HashMap;

/**
 * Created by philipp on 26.06.14.
 */
public class NumberSolution extends BasicSolution {

    private double number;

    private HashMap<Player, Double> chosenSolutions = new HashMap<>(2);

    public NumberSolution(double number, boolean correct) {
        super(String.valueOf(number), correct);
        this.number = number;
    }

    public HashMap<Player, Double> getChosenSolutions() {
        return chosenSolutions;
    }

}
