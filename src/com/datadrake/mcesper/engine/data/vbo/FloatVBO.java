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

package com.datadrake.mcesper.engine.data.vbo;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

/**
 * FloatVBO is just a slightly easier way of managing a VBO for Float data
 */
public class FloatVBO extends VBO {

    private float[] last;
    private FloatBuffer buff;

    /**
     * Constructor
     *
     * @param attrNumber
     *         the index in the VAO for this VBO
     * @param attrWidth
     *         the number of floats per entry
     * @param vboType
     *         the kind of data in this VBO
     * @param initial
     *         the starting value
     */
    public FloatVBO(int attrNumber, int attrWidth, int vboType, float[] initial) {
        super(attrNumber, attrWidth, vboType);
        buff = BufferUtils.createFloatBuffer(initial.length);
        update(initial);
    }

    /**
     * Update the values in this VBO and sync them to the GPU
     *
     * @param next
     *         the new values
     */
    public void update(float[] next) {
        last = next;
        buff.put(next);
        buff.flip();
        GL15.glBindBuffer(vboType, ID);
        GL15.glBufferData(vboType, buff, GL15.GL_STATIC_DRAW);
        if (vboType == GL15.GL_ARRAY_BUFFER) {
            GL20.glVertexAttribPointer(attrNumber, attrWidth, GL11.GL_FLOAT, false, 0, 0);
        }
        GL15.glBindBuffer(vboType, 0);
    }

    /**
     * Get the last value set
     *
     * @return the last value
     */
    public float[] getLast() {
        return last;
    }
}
