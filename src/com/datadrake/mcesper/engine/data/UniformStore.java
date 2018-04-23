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

package com.datadrake.mcesper.engine.data;

import com.datadrake.mcesper.engine.data.uniform.Uniform;

import java.util.HashMap;
import java.util.Map;

/**
 * UniformStore provides a central location for all uniforms
 */
public class UniformStore {

    // Map of Uniform name to object
    private Map<String, Uniform> uniforms;

    /**
     * Constructor
     */
    public UniformStore() {
        uniforms = new HashMap<>();
    }

    /**
     * Add/Update a uniform
     *
     * @param name
     *         the variable name of the uniform
     * @param value
     *         the uniform itself
     */
    public void put(String name, Uniform value) {
        uniforms.put(name, value);
    }

    /**
     * Retrieve an existing uniform
     *
     * @param name
     *         the name of the uniform
     *
     * @return the uniform itself
     */
    public Uniform get(String name) {
        return uniforms.get(name);
    }

    /**
     * Load the current value of a uniform if not already updated
     *
     * @param PID
     *         the shader program ID
     * @param name
     *         the name of the uniform
     */
    public void load(int PID, String name) {
        Uniform current = get(name);
        if (current != null) {
            current.load(PID);
        }
    }

    /**
     * Mark all uniforms as updated
     */
    public void flip() {
        uniforms.forEach((k, v) -> v.flip());
    }
}
