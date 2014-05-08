/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.network;

import socketio.Socket;

/**
 * Created by philipp on 08.05.14.
 */
public abstract class Connection extends Thread {

    private Socket socket;
    private boolean running;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        this.running = true;
        while(running){
            handleRequests();
        }
    }

    protected abstract void handleRequests();

    public boolean isRunning() {
        return running;
    }

    public Socket getSocket() {
        return socket;
    }
}
