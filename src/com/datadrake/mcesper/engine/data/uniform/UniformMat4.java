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

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

/**
 * Scalar Mat4 Uniform for OpenGL
 */
public class UniformMat4 extends Uniform {

    private Matrix4f value;
    private FloatBuffer buff;

    /**
     * Constructor
     *
     * @param name
     *         the shader variable name
     * @param initial
     *         the starting value
     */
    public UniformMat4(String name, Matrix4f initial) {
        super(name);
        buff = BufferUtils.createFloatBuffer(16);
        this.value = initial;
    }

    /**
     * Update the local value of this uniform, mark for upload
     *
     * @param value
     *         the new value
     */
    public void update(Matrix4f value) {
        this.value = value;
        this.updated = true;
    }

    @Override
    public void load(int PID) {
        // Don't Update if unchanged
        if (!updated) {
            return;
        }
        Integer location = GL20.glGetUniformLocation(PID, name);
        if (location > -1) {
            value.get(buff);
            GL20.glUniformMatrix4fv(location, false, buff);
        }
    }
}
