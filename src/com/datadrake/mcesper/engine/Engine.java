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

package com.datadrake.mcesper.engine;

import com.datadrake.mcesper.engine.graphics.WindowManager;
import com.datadrake.mcesper.engine.logic.Player;
import com.datadrake.mcesper.game.Level1;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * The actual game Engine
 */
public class Engine {

    private WindowManager window;
    private Player player;
    private Level level;

    /**
     * Constructor
     */
    public Engine() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Create the window
        window = new WindowManager("M.C.Esper");
        // Setup input
        window.registerInputHandler();
        // Show the window
        window.open(true);
        GL.createCapabilities();

        System.out.println(GL11.glGetString(GL11.GL_VERSION));
        player = new Player();

        level = new Level1();
    }

    /**
     * Finish setup and actually start the game
     */
    public void run() {

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        while (window.isValid()) {
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            level.update();
            window.swap();
            glfwPollEvents();
        }
    }

    /**
     * Cleanup before exit
     */
    public void close() {
        window.close();
        player.free();
        level.free();
        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


}
