/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.server.model.network;

import iquiz.main.controller.MainController;
import iquiz.main.model.Logging;
import iquiz.main.model.game.Game;
import iquiz.main.model.game.Language;
import iquiz.main.model.game.Player;
import iquiz.main.model.game.question.*;
import iquiz.main.model.network.Connection;
import iquiz.main.model.network.Protocol;
import socketio.Socket;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

/**
 * Created by philipp on 08.05.14.
 */
public class ClientConnection extends Connection {

    public ClientConnection(Socket socket) {
        super(socket);
    }

    @Override
    protected boolean authenticate() {
        boolean success = false;

        try {
            // Ensure that both sides are on the same protocol version
            success = this.socket.readLine().equalsIgnoreCase(Protocol.BEGIN_CONNECTION + Protocol.SEPARATOR + MainController.PROTOCOL_VERSION);
            this.socket.write(success ? Protocol.ACCEPT_CONNECTION + "\n" : Protocol.DECLINE_CONNECTION + "\n");

            if (!success) {
                this.socket.write(Protocol.END_CONNECTION + "\n");
                this.socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    protected void doDaemonWork() throws IOException, ClassNotFoundException {
        this.handleInput(this.socket.readLine());
    }

    private void handleInput(String message) throws IOException, ClassNotFoundException {
        Logging.log(Logging.Priority.DEBUG, message);

        if (message.startsWith(Protocol.BEGIN_AUTHENTICATION)) {
            this.handleLogin(message);
        } else if (message.startsWith(Protocol.BEGIN_LOGIN)) {
            this.handleRegistration(message);
        } else if (message.equals("PING")) {
            this.socket.write("PONG\n");
        } else if (message.startsWith(Protocol.BEGIN_REFRESH)) {
            this.handlePushToClient(message);
        } else if(message.startsWith(Protocol.BEGIN_GAME_REQUEST)){
            this.handleGameRequest(message);
        } else if(message.startsWith(Protocol.BEGIN_PUSH)){
            this.handlePullFromClient(message);
        } else {
            Logging.log(Logging.Priority.ERROR, "Cannot handle this yet");
        }
    }

    private void handlePullFromClient(String message) throws IOException, ClassNotFoundException {
        String opponentName = this.socket.readLine();
        BasicQuestion question = (BasicQuestion) inputStream.readObject();
        BasicSolution solution = (BasicSolution) inputStream.readObject();

        if(solution == null){
            solution = new IllegalSolution();
        }

        Logging.log(Logging.Priority.MESSAGE, "Got", solution.getText(), "for", question.getQuestionText());

        if(question instanceof MultipleChoiceQuestion){
            boolean foundOwn = false;
            boolean foundOpponent = false;

            for(Game g : this.getRelatedPlayer().getGames()){
                int index = g.getQuestions().indexOf(question);
                if(index != -1){
                    g.getQuestions().get(index).getChosenAnswers().put(this.getRelatedPlayer(), solution);
                    foundOwn = true;
                }
            }

            for(Game g : Player.get(opponentName).getGames()){
                int index = g.getQuestions().indexOf(question);
                if(index != -1){
                    g.getQuestions().get(index).getChosenAnswers().put(this.getRelatedPlayer(), solution);
                    foundOpponent = true;
                }
            }

            Logging.log(Logging.Priority.MESSAGE, "Saved solution successfully: own=", foundOwn, "opponent=", foundOpponent);
            this.socket.write(Protocol.END_PUSH + "\n");
        }else {
            Logging.log(Logging.Priority.ERROR, "Got illegal type");
        }
    }

    private void handleGameRequest(String message) throws IOException {
        String name = Protocol.split(message)[1];
        Logging.log(Logging.Priority.MESSAGE, "Handling game request of ", this.getRelatedPlayer().getUsername(), " against ", name);
        Player opponent = Player.get(name);

            if (opponent != null && !opponent.equals(this.getRelatedPlayer())) {
                Game.factory(this.getRelatedPlayer(), opponent);

                this.socket.write(Protocol.ACCEPT + Protocol.SEPARATOR + Protocol.END_GAME_REQUEST + "\n");
            }else{
                this.socket.write(Protocol.DECLINE + Protocol.SEPARATOR + Protocol.END_GAME_REQUEST + "\n");
                Logging.log(Logging.Priority.ERROR, "Declined game");
            }
    }

    private void handlePushToClient(String message) throws IOException {
        Logging.log(Logging.Priority.MESSAGE, "Sending updated gamelist");

        Vector<Game> games = this.getRelatedPlayer().getGames();

        outputStream.writeInt(games.size());
        for(Game g : games){
            outputStream.writeObject(g);
        }

        this.socket.write(Protocol.END_REFRESH + "\n");
    }

    private void handleRegistration(String message) throws IOException {
        String[] values = Protocol.split(message);
        String name = values[1];
        String username = values[2];
        String passwordHash = values[3];
        String email = values[4];
        Player.Sex sex = Player.Sex.valueOf(values[5]);
        Date birthday = new Date(Long.valueOf(values[6]));
        Language language = Language.valueOf(values[7]);

        Player result = Player.factory(name, username, passwordHash, email, sex, language, birthday);

            if (result == null) {
                this.socket.write(Protocol.DECLINE + "\n");
            } else {
                this.socket.write(Protocol.ACCEPT + "\n");

                this.setRelatedPlayer(result);

                outputStream.writeObject(result);
            }
    }

    private void handleLogin(String message) throws IOException {
        String[] values = Protocol.split(message);

        Logging.log(Logging.Priority.VERBOSE, "Authenticating");

        String username = values[1];
        String passwordHash = values[2];

        Player player = Player.get(username);

        if(player != null && player.getPasswordHash().equals(passwordHash)){
            this.outputStream.writeObject(player);
            this.socket.write(Protocol.ACCEPT_AUTHENTICATION + "\n");
            this.setRelatedPlayer(player);
        }else{
            this.socket.write((Protocol.DECLINE_AUTHENTICATION) + "\n");
        }
    }
}
