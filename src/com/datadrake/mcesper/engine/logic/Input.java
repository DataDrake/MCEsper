/*
 * Copyright 2018 Bryan T. Meyers <root@datadrake.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.datadrake.mcesper.engine.logic;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private static Player player;

    public static void setPlayer(Player user) {
        player = user;
    }

    public static void keyhandler(long window, int key, int scancode, int action, int mods) {
        switch( action ) {
        case GLFW_RELEASE:
            switch( key ) {
            case GLFW_KEY_ESCAPE:
                glfwSetWindowShouldClose(window, true);
                break;
            }
            break;
        case GLFW_PRESS:
        case GLFW_REPEAT:
            switch( key ) {
            case GLFW_KEY_W: // Up
                player.move(new float[]{0,0,-0.5f});
                break;
            case GLFW_KEY_S: // Down
                player.move(new float[]{0,0,0.5f});
                break;
            case GLFW_KEY_A: // Left
                player.move(new float[]{-0.5f,0,0});
                break;
            case GLFW_KEY_D: // Right
                player.move(new float[]{0.5f,0,0});
                break;
            }
            break;
        }

    }
}
