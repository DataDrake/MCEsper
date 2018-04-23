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

package com.datadrake.mcesper.engine.graphics.shaders.programs;

import com.datadrake.mcesper.engine.data.UniformStore;
import com.datadrake.mcesper.engine.data.uniform.UniformMat4;
import com.datadrake.mcesper.engine.data.uniform.UniformVec3Array;
import com.datadrake.mcesper.engine.graphics.shaders.ShaderStore;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

/**
 * WorldProgram is the ShaderProgram used to render the world
 */
public class WorldProgram extends ShaderProgram {


    /**
     * Constructor
     *
     * @param shaders
     *         common ShaderStore
     * @param uniforms
     *         common UniformStore
     */
    public WorldProgram(ShaderStore shaders, UniformStore uniforms) {
        super(shaders, uniforms);
    }

    @Override
    public void loadShaders() {
        loadShader(SHADER_PREFIX + "/map_vertex.glsl", GL20.GL_VERTEX_SHADER);
        loadShader(SHADER_PREFIX + "/map_geometry.glsl", GL32.GL_GEOMETRY_SHADER);
        loadShader(SHADER_PREFIX + "/map_fragment.glsl", GL20.GL_FRAGMENT_SHADER);
    }

    @Override
    public void bindUniforms() {
        bindUniform("transform");
        bindUniform("view");
        bindUniform("projection");

        UniformVec3Array vertices = new UniformVec3Array("vertices", new float[] {
             1f, 1f,-1f,
            -1f, 1f,-1f,
             1f, 1f, 1f,
            -1f, 1f, 1f,
            -1f,-1f, 1f,
            -1f,-1f,-1f,
             1f,-1f,-1f,
             1f,-1f, 1f
        });
        this.uniforms.put("vertices", vertices);
        bindUniform("vertices");
    }

    @Override
    public void bindAttributes() {
        bindAttribute(0, "position");
    }

    @Override
    public void loadRenderables() {
        loadRenderable(new Map());
    }
}
