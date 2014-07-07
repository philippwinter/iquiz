/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.network;

import iquiz.main.model.Logging;
import iquiz.main.model.game.Player;
import socketio.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by philipp on 08.05.14.
 */
public abstract class Connection extends Thread {

    protected Socket socket;
    protected ObjectOutputStream outputStream;
    protected ObjectInputStream inputStream;

    protected boolean running;
    private Player relatedPlayer = null;

    public Connection(Socket socket) {
        this.setDaemon(true);
        this.setName(this.getClass().getSimpleName() + " (Thread "  + this.getId() + ")");
        this.socket = socket;
        this.socket.connect();

        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void run() {
        if (this.authenticate()) {
            Logging.log(Logging.Priority.VERBOSE, "Success");
            this.running = true;
            while(true){
                if(running){
                    try {
                        doDaemonWork();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new RuntimeException("Could not establish connection to " + socket.getInetAddress() + " @ port " + socket.getPort() + "!\nAuthentication failed!");
        }
    }

    protected abstract boolean authenticate();

    protected abstract void doDaemonWork() throws IOException, ClassNotFoundException;

    public boolean isRunning() {
        return running;
    }

    public void halt() {
        this.running = false;
        try {
            this.socket.write(Protocol.END_CONNECTION + "\n");
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void continueExecution() {
        if(this.socket.connect()) {
            this.running = true;
        }else{
            throw new RuntimeException("Could not continue execution");
        }
    }

    public Player getRelatedPlayer() {
        return relatedPlayer;
    }

    protected void setRelatedPlayer(Player relatedPlayer) {
        this.relatedPlayer = relatedPlayer;
    }
}
