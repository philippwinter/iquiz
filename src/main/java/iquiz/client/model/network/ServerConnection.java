/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.client.model.network;

import iquiz.client.controller.ClientController;
import iquiz.main.controller.MainController;
import iquiz.main.model.Helper;
import iquiz.main.model.Logging;
import iquiz.main.model.game.Language;
import iquiz.main.model.game.Player;
import iquiz.main.model.network.Connection;
import iquiz.main.model.network.Protocol;
import socketio.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

/**
 * Created by philipp on 08.05.14.
 */
public class ServerConnection extends Connection {

    public static final String SERVER_ADDRESS = "localhost";

    private ServerConnection(Socket socket) {
        super(socket);
    }

    public ServerConnection() {
        this(new Socket(SERVER_ADDRESS, ClientController.PORT));
    }

    @Override
    protected boolean authenticate() {
        boolean success = false;

        try {
            this.socket.write(Protocol.BEGIN_CONNECTION + Protocol.SEPARATOR + MainController.PROTOCOL_VERSION + "\n");
            success = this.socket.readLine().equals(Protocol.ACCEPT_CONNECTION);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!success) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    @Override
    protected void doDaemonWork() {
        // The client side has to do nothing at all constantly
    }

    public void doPing() throws IOException {
        this.socket.write("PING\n");
        Logging.log(Logging.Priority.DEBUG, this.socket.readLine());
    }

    public boolean login(String username, String password){
        boolean success = false;

        try {
            this.socket.write(
                    Protocol.BEGIN_AUTHENTICATION +
                    Protocol.join(username, Helper.generateHash(password)) +
                    Protocol.END_AUTHENTICATION +
                    "\n"
            );

            success = this.socket.readLine().equals(Protocol.ACCEPT_AUTHENTICATION);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientController.getInstance().loginCallback(success);

        return success;
    }

    public void register(String name, String username, String password, String email, Player.Sex sex, Date date, Language language) {
        boolean success = false;

        try {
            this.socket.write(
                Protocol.BEGIN_LOGIN +
                Protocol.join(
                        name,
                        username,
                        Helper.generateHash(password),
                        email,
                        sex.toString(),
                        String.valueOf(date.getTime()),
                        language.toString()
                ) +
                Protocol.END_LOGIN +
                "\n"
            );

            success = this.socket.readLine().equals(Protocol.ACCEPT);

            if(success){
                ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
                Player player = (Player) objectInputStream.readObject();

                Logging.log(Logging.Priority.MESSAGE, player);
                this.setCurrentPlayer(player);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ClientController.getInstance().registrationCallback(success);
    }

    public boolean doDataRefresh() {
        boolean success = false;

        try {
            this.socket.write(Protocol.BEGIN_REFRESH + "\n");

            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            Player player = (Player) objectInputStream.readObject();

            Logging.log(Logging.Priority.MESSAGE, "Refreshed data", player);

            success = this.socket.readLine().equals(Protocol.END_REFRESH);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return success;
    }
}
