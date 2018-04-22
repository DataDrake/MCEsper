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

package com.datadrake.graphics.shaders;

import org.lwjgl.opengl.GL20;

/**
 * WorldProgram is the ShaderProgram used to render the world
 */
public class WorldProgram extends ShaderProgram {

    /**
     * Constructor
     *
     * @param store
     *         common ShaderStore
     */
    public WorldProgram(ShaderStore store) {
        super(store);
    }

    @Override
    public void shaders() {
        bindShader("src/com/datadrake/graphics/shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);
        bindShader("src/com/datadrake/graphics/shaders/fragment.glsl", GL20.GL_FRAGMENT_SHADER);
    }

    @Override
    public void attributes() {
        bindAttribute(0, "position");
    }
}
