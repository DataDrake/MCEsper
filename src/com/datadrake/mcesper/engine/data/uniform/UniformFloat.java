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

/**
 * Scalar Float Uniform for OpenGL
 */
public class UniformFloat extends Uniform {

    private float value;

    /**
     * Constructor
     *
     * @param name
     *         the shader variable name
     * @param initial
     *         the starting value
     */
    public UniformFloat(String name, float initial) {
        super(name);
        this.value = initial;
    }

    /**
     * Update the local value of this uniform, mark for upload
     *
     * @param value
     *         the new value
     */
    public void update(float value) {
        this.value = value;
        this.updated = true;
    }

    @Override
    public void load(int PID) {
        // Don't Update if unchanged
        if (!updated) {
            return;
        }
        Integer location = programLocations.get(PID);
        if (location != null) {
            GL20.glUniform1f(location, value);
        }
    }
}
