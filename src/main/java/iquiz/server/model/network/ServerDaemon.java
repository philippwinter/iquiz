/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.server.model.network;

import iquiz.main.model.Logging;
import iquiz.server.controller.ServerController;

import java.io.IOException;

import socketio.ServerSocket;
import socketio.Socket;
import java.util.Vector;

/**
 * Created by philipp on 08.05.14.
 */
public class ServerDaemon extends Thread {

    private ServerSocket serverSocket;
    private Vector<ClientConnection> connections;
    private boolean running = false;

    public ServerDaemon() {
        this.setDaemon(true);
        this.connections = new Vector<>();

        try {
            this.serverSocket = new ServerSocket(ServerController.PORT);
            Logging.log(Logging.Priority.DEBUG, "Server daemon listening on port " + ServerController.PORT + " now!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void haltAll() {
        this.running = false;
        for(ClientConnection con : this.connections){
            con.halt();
        }
    }

    public void continueAll() {
        this.running = true;
        for(ClientConnection con : this.connections){
            con.continueExecution();
        }
    }

    public Vector<ClientConnection> getConnections() {
        return connections;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        this.running = true;
        while(this.running){
            try {
                Socket socket = serverSocket.accept();
                ClientConnection connection = new ClientConnection(socket);
                Logging.log(Logging.Priority.MESSAGE, "Got new connection");
                connection.start();
                this.connections.add(connection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
