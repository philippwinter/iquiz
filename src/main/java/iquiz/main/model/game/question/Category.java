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
public enum Category {

    SPORTS,
    SCIENCE,
    ECONOMY,
    SOCIETY,
    INFLUENCE,
    HISTORY,
    NATURE,
    INTERNATIONAL,
    RELIGION,
    COMPUTERS;

    public static Category any() {
        Category[] categories = Category.values();

        return categories[(int) (Math.random()*categories.length)];
    }

    public String toCapitalizedString() {
        String s = this.toString();
        StringBuilder sb = new StringBuilder(s.length());
        sb.append(s.charAt(0));
        sb.append(s.substring(1, s.length()).toLowerCase());

        return sb.toString();
    }
}
