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
package iquiz.client.controller;

import iquiz.client.pc.view.MainView;
import iquiz.main.controller.BasicController;
import iquiz.main.controller.MainController;

/**
 *
 * @author HTS1205U13
 */
public class ClientController implements BasicController {

    @Override
    public void initialize() {
        switch(MainController.OS){
            case UNKNOWN:
            case WINDOWS:
            case MAC_OS_X:
                MainView.create();
                break;
            case ANDROID:
                // TODO: Initialize android application
                break;
            default:
                throw new RuntimeException("Could not determine view suitable for OS");
        }
    }
    
}
