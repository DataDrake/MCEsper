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

import org.lwjgl.opengl.GL15;

/**
 * VBO is a representation of a OpenGL VBO
 */
public abstract class VBO {

    int ID;
    int attrNumber;
    int attrWidth;
    int vboType;

    /**
     * Constructor
     *
     * @param attrNumber
     *         the index in the VAO for this VBO
     * @param attrWidth
     *         the number of floats per entry
     */
    VBO(int attrNumber, int attrWidth, int vboType) {
        ID = GL15.glGenBuffers();
        this.attrNumber = attrNumber;
        this.attrWidth = attrWidth;
        this.vboType = vboType;
    }

    /**
     * Deallocate this VBO on the GPU
     */
    public void free() {
        GL15.glDeleteBuffers(ID);
    }

    /**
     * Bind this VBO
     */
    public void bind() {
        GL15.glBindBuffer(vboType, ID);
    }

    /**
     * Unbind this VBO
     */
    public void unbind() {
        GL15.glBindBuffer(vboType, 0);
    }
}
