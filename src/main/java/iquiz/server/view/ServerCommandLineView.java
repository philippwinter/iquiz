/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.server.view;

import iquiz.server.controller.ServerController;

import java.util.Scanner;

/**
 * Created by philipp on 05.07.14.
 */
public class ServerCommandLineView extends Thread {

    private Scanner input = new Scanner(System.in);
    private boolean running;

    public ServerCommandLineView(){
        this.setDaemon(false);
    }

    @Override
    public void run() {
        this.running = true;
        while(running){
            String message = input.nextLine();

            switch(message.toUpperCase()){
                case "STOP": {
                    ServerController.getInstance().getServerDaemon().haltAll();
                    running = false; // Program should exit now, as all non-daemon threads are finished
                }
            }
        }
    }
}
