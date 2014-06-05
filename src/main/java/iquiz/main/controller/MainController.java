/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iquiz.main.controller;

import iquiz.client.controller.ClientController;
import iquiz.main.model.Logging;
import iquiz.main.model.game.Language;
import iquiz.main.model.game.Player;
import iquiz.server.controller.ServerController;

/**
 *
 * @author HTS1205U13
 */
public class MainController implements BasicController {

    public static OperatingSystem OS;
    public static final double VERSION = 0.1;
    public static final double PROTOCOL_VERSION = 1.0;

    private static MainController instance;
    private static MainController.Type type;
    
    private ServerController server;
    private ClientController client;
    
    static {
        MainController.type = MainController.Type.CLIENT_AND_SERVER;

        String osIdentifier = System.getProperty("os.name");

        if(osIdentifier.contains("Mac OS X")){
            MainController.OS = OperatingSystem.MAC_OS_X;
        }else if(osIdentifier.contains("Windows")){
            MainController.OS = OperatingSystem.WINDOWS;
        }else if(osIdentifier.contains("Android")){
            MainController.OS = OperatingSystem.ANDROID;
        }else{
            MainController.OS = OperatingSystem.UNKNOWN;
        }

        Logging.log(Logging.Priority.VERBOSE, "System type: " + MainController.OS);
    }

    public static MainController getInstance() {
        if(instance == null){
            instance = new MainController();
            instance.initialize();
        }
        return instance;
    }
    
    @Override
    public void initialize() {
        switch (MainController.type) {
            case CLIENT:
                initializeClient();
                break;
            case SERVER:
                initializeServer();
                break;
            case CLIENT_AND_SERVER:
                initializeServer();
                initializeClient();
                break;
        }
    }

    public static Type getType() {
        return type;
    }

    public ServerController getServer() {
        return server;
    }

    public ClientController getClient() {
        return client;
    }

    private enum Type {
        CLIENT,
        SERVER,
        CLIENT_AND_SERVER
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Force initializing of the MainController
        MainController.getInstance();
    }

    private void initializeClient() {
        this.client = ClientController.getInstance();
        this.client.initialize();
    }

    private void initializeServer() {
        this.server = ServerController.getInstance();
        this.server.initialize();
    }
}
