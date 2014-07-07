/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by philipp on 17.05.14.
 */
public class Logging {

    public final static int VERBOSE = 0x01;
    public final static int DEBUG = 0x02;
    public final static int WARNING = 0x04;
    public final static int ERROR = 0x08;

    public static int OUTPUT_LEVEL = VERBOSE | DEBUG | WARNING | ERROR;

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

    public static synchronized void log(Priority priority, Object... messages){
        String timeString = dateFormatter.format(new Date());

        Thread currentThread = Thread.currentThread();
        StackTraceElement[] stackTraceElements = currentThread.getStackTrace();
        StackTraceElement caller = stackTraceElements[2];

        StringBuilder txt = new StringBuilder(100);

        for(Object message : messages){
            txt.append(message == null ? "null" : message.toString().replace("\n", "\n\t"));
            txt.append("\t");
        }

        String output = String.format("[%s @ %s] \n\t%s", timeString, caller, txt.toString());

        if(priority == Priority.VERBOSE && (VERBOSE & OUTPUT_LEVEL) != 0 || priority == Priority.DEBUG && (DEBUG & OUTPUT_LEVEL) != 0) {
            System.out.println(output);
        } else if(priority == Priority.WARNING && (WARNING & OUTPUT_LEVEL) != 0 || priority == Priority.ERROR && (ERROR & OUTPUT_LEVEL) != 0){
            System.err.println(output);
        } else if(priority == Priority.MESSAGE) {
            System.out.println(output);
        }
    }


    public static enum Priority {
        MESSAGE, // Always shown
        VERBOSE,
        DEBUG,
        WARNING,
        ERROR
    }
}
