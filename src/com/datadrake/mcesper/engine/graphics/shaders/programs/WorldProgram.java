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
import com.datadrake.mcesper.engine.graphics.WindowManager;
import com.datadrake.mcesper.engine.graphics.shaders.ShaderStore;
import com.datadrake.mcesper.util.MatrixUtil;
import org.lwjgl.opengl.GL20;

/**
 * WorldProgram is the ShaderProgram used to render the world
 */
public class WorldProgram extends ShaderProgram {

    private static final float FIELD_OF_VIEW = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;

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
        loadShader(SHADER_PREFIX + "/map_fragment.glsl", GL20.GL_FRAGMENT_SHADER);
    }

    @Override
    public void bindUniforms() {
        UniformMat4 transform = new UniformMat4("transform", MatrixUtil.transform(new float[]{0, 0, -2}, new float[]{0, 0, 0}, 1f));
        bindUniform("transform", transform);

        UniformMat4 projection = new UniformMat4("projection", MatrixUtil.project(FIELD_OF_VIEW, NEAR_PLANE, FAR_PLANE, WindowManager.getWidth(), WindowManager.getHeight()));
        bindUniform("projection", projection);
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
