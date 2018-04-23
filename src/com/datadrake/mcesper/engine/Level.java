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

import com.datadrake.mcesper.engine.data.UniformStore;
import com.datadrake.mcesper.engine.graphics.shaders.ShaderStore;
import com.datadrake.mcesper.engine.graphics.shaders.programs.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Levels are completely independent game environments
 */
public abstract class Level {

    private UniformStore uniforms;
    private ShaderStore shaders;
    private List<ShaderProgram> programs;


    /**
     * Constructor
     */
    public Level() {
        uniforms = new UniformStore();
        shaders = new ShaderStore();
        programs = new ArrayList<>();

        loadPrograms();
    }

    /**
     * Add a ShaderProgram to this Level
     *
     * @param program
     *         the program to add
     */
    protected void loadProgram(ShaderProgram program) {
        programs.add(program);
    }

    /**
     * Load all of the ShaderPrograms
     */
    public abstract void loadPrograms();


    /**
     * Update this scene
     */
    void update() {
        programs.forEach((program) -> {
            program.run();
            program.stop();
        });
        uniforms.flip();
    }

    /**
     * Cleanup this scene
     */
    public void free() {
        programs.forEach(ShaderProgram::free);
        shaders.free();
    }

    /**
     * Accessor for UniformStore
     *
     * @return the UniformStore
     */
    protected UniformStore getUniforms() {
        return uniforms;
    }

    /**
     * Accessor for ShaderStore
     *
     * @return the ShaderStore
     */
    protected ShaderStore getShaders() {
        return shaders;
    }

}
