/*
 * Copyright 2018 Bryan T. Meyers <root@datadrake.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.datadrake;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.system.MemoryUtil.NULL;

/*
    WindowManager is an abstraction for the OpenGL Window object that makes doing things easier
 */
public class WindowManager {
    // The window ID
    private long ID;
    private int width, height;

    /*
        Constructor

        @param title - the title for the window
     */
    public WindowManager(String title) {
        long monitor = glfwGetPrimaryMonitor();
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(monitor);
        this.width = vidmode.width();
        this.height = vidmode.height();
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable
        ID = glfwCreateWindow(width, height, title, monitor, NULL);
        if ( ID == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
    }

    /*
        Display the window for all to see

        @param vsync - Enable vertical sync
     */
    public void open(boolean vsync) {
        // Make the OpenGL context current
        glfwMakeContextCurrent(ID);
        // Enable v-sync
        if (vsync)
            glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(ID);
    }

    /*
        Close this window entirely
     */
    public void close() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(ID);
        glfwDestroyWindow(ID);
    }

    /*
        Display the next frame
     */
    public void swap() {
        glfwSwapBuffers(ID);
    }

    /*
        Register an input handler for dealing with User input
        TODO: Make this use an actual class
     */
    public void registerInputHandler() {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(ID, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
    }

    /*
        Check if this window should still be displayed

        @returns true iff the window should remain open
     */
    public boolean isValid() {
        return !glfwWindowShouldClose(ID);
    }
}
