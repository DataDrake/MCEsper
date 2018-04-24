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

import com.datadrake.mcesper.engine.graphics.WindowManager;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private static Player player;
    private static double lastX;
    private static double lastY;
    private static boolean mouseFirst = true;

    private static float width = (float)WindowManager.getWidth();
    private static float height = (float)WindowManager.getHeight();

    public static void setPlayer(Player user) {
        player = user;
    }

    public static void cursorhandler(long window, double xpos, double ypos) {
/*        if (mouseFirst) {
            lastX = xpos;
            lastY = ypos;
            mouseFirst = false;
        }

        float dx = (float)xpos - (float)lastX;
        float dy = (float)lastY - (float)ypos;
        lastX = xpos;
        lastY = ypos;*/

        float dx = (float)xpos/width - 0.5f;
        float dy = 0.5f - (float)ypos/height;

        player.pivot(dx, dy);
        glfwSetCursorPos(window, WindowManager.getWidth()/2, WindowManager.getHeight()/2);
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
                player.move(Player.STRAFE_FORWARD);
                break;
            case GLFW_KEY_S: // Down
                player.move(Player.STRAFE_BACK);
                break;
            case GLFW_KEY_A: // Left
                player.move(Player.STRAFE_LEFT);
                break;
            case GLFW_KEY_D: // Right
                player.move(Player.STRAFE_RIGHT);
                break;
            }
            break;
        }

    }
}
