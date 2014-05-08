/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.server.model.network;

import iquiz.main.model.network.Connection;
import socketio.Socket;

/**
 * Created by philipp on 08.05.14.
 */
public class ServerConnection extends Connection {

    public ServerConnection(Socket socket) {
        super(socket);
    }

    @Override
    protected void handleRequests() {
        // TODO: Implement protocol
        throw new RuntimeException("Not supported yet");
    }
}
