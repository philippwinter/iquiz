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
package iquiz.server.controller;

import iquiz.main.controller.BasicController;
import iquiz.server.model.network.ServerDaemon;
import iquiz.server.view.ServerCommandLineView;

/**
 *
 * @author HTS1205U13
 */
public class ServerController implements BasicController {

    public static final int PORT = 1234;
    private ServerDaemon serverDaemon;
    private static ServerController instance;

    public static ServerController getInstance() {
        if(instance == null){
            instance = new ServerController();
        }
        return instance;
    }

    @Override
    public void initialize() {
        this.serverDaemon = new ServerDaemon();
        this.serverDaemon.start();

        new ServerCommandLineView().start();
    }

    public ServerDaemon getServerDaemon() {
        return serverDaemon;
    }
    
}
