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

package com.datadrake;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * FloatVBO is just a slightly easier way of managing a VBO for Float data
 */
public class UintVBO {

    private int ID;
    private int attrNumber;
    private int attrWidth;
    private int vboType;
    private int dataType;
    private int[] last;
    private IntBuffer buff;

    /**
     * Constructor
     *
     * @param attrNumber
     *         the index in the VAO for this VBO
     * @param attrWidth
     *         the number of floats per entry
     * @param initial
     *         the starting value
     */
    public UintVBO(int attrNumber, int attrWidth, int vboType, int dataType, int[] initial) {
        ID = GL15.glGenBuffers();
        this.attrNumber = attrNumber;
        this.attrWidth = attrWidth;
        this.vboType = vboType;
        this.dataType = dataType;
        buff = BufferUtils.createIntBuffer(initial.length);
        update(initial);
    }

    /**
     * Update the values in this VBO and sync them to the GPU
     *
     * @param next the new values
     */
    public void update(int[] next) {
        last = next;
        buff.put(next);
        buff.flip();
        GL15.glBindBuffer(vboType, ID);
        GL15.glBufferData(vboType, buff, GL15.GL_STATIC_DRAW);
        if (vboType == GL15.GL_ARRAY_BUFFER)
            GL20.glVertexAttribPointer(attrNumber, attrWidth, dataType, false, 0, 0);
        GL15.glBindBuffer(vboType, 0);
    }

    /**
     * Get the last value set
     *
     * @return the last value
     */
    public int[] getLast() {
        return last;
    }

    /**
     * Deallocate this VBO on the GPU
     */
    public void free() {
        GL15.glDeleteBuffers(ID);
    }

    public void bind() {
        GL15.glBindBuffer(vboType, ID);
    }

    public void unbind() {
        GL15.glBindBuffer(vboType, 0);
    }
}
