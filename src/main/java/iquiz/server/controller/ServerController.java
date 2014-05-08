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

/**
 *
 * @author HTS1205U13
 */
public class ServerController implements BasicController {

    public static final int PORT = 1234;

    private ServerDaemon serverDaemon;

    @Override
    public void initialize() {
        this.serverDaemon = new ServerDaemon();
        this.serverDaemon.start();
    }
    
}
