/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.server.model.network;

import iquiz.client.model.network.ClientConnection;
import iquiz.main.model.network.Connection;
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
        this.connections = new Vector<>();

        try {
            this.serverSocket = new ServerSocket(ServerController.PORT);
        } catch (IOException e) {
            e.printStackTrace();
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
                connection.start();
                this.connections.add(connection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
