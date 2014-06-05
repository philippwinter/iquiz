/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.network;

import java.util.regex.Pattern;

/**
 * Created by philipp on 08.05.14.
 */
public class Protocol {

    public static final String SEPARATOR = "|:|";

    public static final String BEGIN_CONNECTION = "--<BEGIN_CONNECTION>--";
    public static final String END_CONNECTION = "-<END_CONNECTION>-";

    public static final String ACCEPT_CONNECTION = "-<ACCEPT_CONNECTION>-";
    public static final String DECLINE_CONNECTION = "-<DECLINE_CONNECTION>-";

    public static final String DECLINED_VERSION = "-<DECLINED_VERSION>-";

    public static final String BEGIN_AUTHENTICATION = "-<BEGIN_AUTHENTICATION>-";
    public static final String END_AUTHENTICATION = "-<END_AUTHENTICATION>-";

    public static final String ACCEPT_AUTHENTICATION = "-<ACCEPT_AUTHENTICATION>-";
    public static final String DECLINE_AUTHENTICATION = "-<DECLINE_AUTHENTICATION>-";

    public static final String BEGIN_LOGIN = "-<BEGIN_LOGIN>-";
    public static final String END_LOGIN = "-<END_LOGIN>-";
    public static final String ACCEPT = "-<ACCEPT>-";
    public static final String DECLINE = "-<DECLINE>-";

    public static final String BEGIN_REFRESH = "-<BEGIN_REFRESH>-";
    public static final String END_REFRESH = "-<END_REFRESH>-";

    public static String[] split(String message){
        return message.split(Pattern.quote(SEPARATOR));
    }

    public static String join(String... values){
        StringBuilder sb = new StringBuilder(values.length * 10);
        for(int i = 0; i < values.length; i++){
            if(i == 0){
                sb.append(Protocol.SEPARATOR);
            }
            sb.append(values[i]).append(Protocol.SEPARATOR);
        }

        return sb.toString();
    }
}
