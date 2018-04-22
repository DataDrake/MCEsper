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

import java.util.HashSet;
import java.util.Set;

/**
 * ShaderProgram manages a sequence of shaders as on OpenGL Shader Program
 */
public abstract class ShaderProgram {

    // OpenGL ShaderProgram ID
    private int ID;
    // Dependent Shader IDs
    private Set<Integer> shaderIDs;
    // Shader Store
    private ShaderStore store;

    /**
     * Constructor
     *
     * @param store
     *         the common shader store
     */
    public ShaderProgram(ShaderStore store) {
        shaderIDs = new HashSet<>();
        this.store = store;
        ID = GL20.glCreateProgram();
        shaders();
        GL20.glLinkProgram(ID);
        GL20.glValidateProgram(ID);
        attributes();
    }

    /**
     * Bind a shader to this program
     *
     * @param path
     *         the filepath to the shader
     * @param type
     *         the type of shader
     */
    public void bindShader(String path, int type) {
        int sID = store.register(path, type);
        shaderIDs.add(sID);
        GL20.glAttachShader(ID, sID);
    }

    /**
     * Bind all shaders in this method
     */
    public abstract void shaders();


    /**
     * Bind an attribute name to an index in the VAO
     *
     * @param index
     *         VAO index
     * @param name
     *         shader variable name
     */
    public void bindAttribute(int index, String name) {
        GL20.glBindAttribLocation(ID, index, name);
    }

    /**
     * Bind all attributes in this method
     */
    public abstract void attributes();

    /**
     * Execute this program
     */
    public void run() {
        GL20.glUseProgram(ID);
    }

    /**
     * Terminate this program
     */
    public void stop() {
        GL20.glUseProgram(0);
    }

    /**
     * Clean up shaders and remove this program
     */
    public void free() {
        stop();
        shaderIDs.forEach((sID) -> {
            GL20.glDetachShader(ID, sID);
            store.unregister(sID);
        });
        GL20.glDeleteProgram(ID);
    }

}
