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
import iquiz.main.model.game.Game;
import iquiz.main.model.game.Language;
import iquiz.main.model.game.Player;
import iquiz.main.model.game.question.BasicQuestion;
import iquiz.main.model.game.question.BasicSolution;
import iquiz.main.model.network.Connection;
import iquiz.main.model.network.Protocol;
import socketio.Socket;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

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

            Player player = (Player) inputStream.readObject();

            success = this.socket.readLine().equals(Protocol.ACCEPT_AUTHENTICATION) && player != null;

            if(success){
                this.setRelatedPlayer(player);
            }
        } catch (IOException | ClassNotFoundException e) {
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
                Player player = (Player) inputStream.readObject();

                Logging.log(Logging.Priority.MESSAGE, player);
                this.setRelatedPlayer(player);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ClientController.getInstance().registrationCallback(success);
    }

    public boolean doDataPull() {
        boolean success = false;

        try {
            this.socket.write(Protocol.BEGIN_REFRESH + "\n");

            int sizeOfRunningGames = inputStream.readInt();

            Vector<Game> newGames = new Vector<>(sizeOfRunningGames);

            for(int i = 0; i < sizeOfRunningGames; i++){
                Game g = (Game) inputStream.readObject();
                newGames.add(g);
            }

            success = this.socket.readLine().equals(Protocol.END_REFRESH);
            Logging.log(Logging.Priority.MESSAGE, "Expected size", sizeOfRunningGames, "Real size", newGames.size());

            if(success){
                this.getRelatedPlayer().setGames(newGames);
                if(this.getRelatedPlayer().getGames() == newGames){
                    Logging.log(Logging.Priority.MESSAGE, "Refreshed data");
                }else{
                    Logging.log(Logging.Priority.ERROR, "new games not changed correctly");
                }
            }else{
                Logging.log(Logging.Priority.ERROR, "Refreshed data is corrupt", newGames);
            }

            ClientController.getInstance().pullDataCallback(success);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean doRequestForGame(String opponent) {
        boolean success = false;

        try {
            this.socket.write(Protocol.BEGIN_GAME_REQUEST + Protocol.SEPARATOR + opponent + "\n");

            String message = this.socket.readLine();
            success = message.startsWith(Protocol.ACCEPT);

            if(success){
                this.doDataPull();
            }else{
                Logging.log(Logging.Priority.ERROR, "Message was",message);
            }

            Logging.log(Logging.Priority.MESSAGE, "Game created:", success ? "true" : "false");
        } catch (IOException e){
            e.printStackTrace();
        }

        return success;
    }

    public void doPush(BasicQuestion question, BasicSolution solution) {
        boolean success;

        try {
            this.socket.write(Protocol.BEGIN_PUSH + Protocol.SEPARATOR + "\n");

            this.outputStream.writeObject(question);
            this.outputStream.writeObject(solution);

            success = this.socket.readLine().startsWith(Protocol.END_PUSH);

            ClientController.getInstance().pushSolutionCallback(success);
        }catch (IOException e){
            e.printStackTrace();
            ClientController.getInstance().pushSolutionCallback(false);
        }
    }
}
