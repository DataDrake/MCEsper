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

package com.datadrake.mcesper.engine.data.uniform;

import org.lwjgl.opengl.GL20;

import java.util.HashMap;
import java.util.Map;

/**
 * Uniform is a representation of a OpenGL Uniform
 */
public abstract class Uniform {

    // Name of the uniform, must be the same for all loadShaders
    String name;
    // True if it has been modified since last load
    boolean updated;
    // Mapping of PID to Location
    Map<Integer, Integer> programLocations;

    /**
     * Constructor
     *
     * @param name
     *         variable name for this uniform
     */
    Uniform(String name) {
        this.name = name;
        this.updated = true;
        this.programLocations = new HashMap<>();
    }

    /**
     * Associate a program with this Uniform
     *
     * @param PID
     *         the program ID
     */
    public void attachProgram(int PID) {
        int location = GL20.glGetUniformLocation(PID, name);
        programLocations.put(PID, location);
    }

    /**
     * Load the current value to the GPU if it hasn't yet been updated
     *
     * @param PID
     *         the program ID of the current program
     */
    public abstract void load(int PID);

    /**
     * Mark this uniform as updated on the GPU
     */
    public void flip() {
        this.updated = false;
    }

}
