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

package com.datadrake.mcesper.engine.graphics;

import com.datadrake.mcesper.engine.logic.Input;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * WindowManager is an abstraction for the OpenGL Window object that makes doing things easier
 */
public class WindowManager {

    // singleton
    private static WindowManager manager;
    // the window ID
    private long ID;
    //window dimensions
    private int width, height;

    /**
     * Constructor
     *
     * @param title
     *         the title for the window
     */
    private WindowManager(String title) {
        long monitor = glfwGetPrimaryMonitor();
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(monitor);
        this.width = vidmode.width();
        this.height = vidmode.height();
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable
        ID = glfwCreateWindow(width, height, title, monitor, NULL);
        if (ID == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        glfwSetInputMode(ID, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    /**
     * Singleton Constructor
     *
     * @param title
     *         the name of the window
     */
    public static void create(String title) {
        if (manager == null) {
            manager = new WindowManager(title);
        }
    }

    /**
     * Display the window for all to see
     *
     * @param vsync
     *         Enable vertical sync
     */
    public static void open(boolean vsync) {
        // Make the OpenGL context current
        glfwMakeContextCurrent(manager.ID);
        // Enable v-sync
        if (vsync) {
            glfwSwapInterval(1);
        }
        // Make the window visible
        glfwShowWindow(manager.ID);

    }

    /**
     * Close this window entirely
     */
    public static void close() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(manager.ID);
        glfwDestroyWindow(manager.ID);
    }

    /**
     * Display the next frame
     */
    public static void swap() {
        glfwSwapBuffers(manager.ID);
    }

    /**
     * Register an input handler for dealing with User input
     */
    public static void registerInputHandler() {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(manager.ID, Input::keyhandler);
        glfwSetCursorPosCallback(manager.ID, Input::cursorhandler);
    }

    /**
     * Check if this window should still be displayed
     */
    public static boolean isValid() {
        return !glfwWindowShouldClose(manager.ID);
    }

    /**
     * Accessor
     *
     * @return the window width
     */
    public static int getWidth() {
        return manager.width;
    }

    /**
     * Accessor
     *
     * @return the window height
     */
    public static int getHeight() {
        return manager.height;
    }

}
