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
import com.datadrake.mcesper.engine.graphics.Renderable;
import com.datadrake.mcesper.engine.graphics.shaders.ShaderStore;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ShaderProgram manages a sequence of loadShaders as on OpenGL Shader Program
 */
public abstract class ShaderProgram {

    final static String SHADER_PREFIX = "src/com/datadrake/mcesper/engine/graphics/shaders/glsl";

    // OpenGL ShaderProgram ID
    private int ID;

    // Dependent Shader IDs
    private Set<Integer> shaderIDs;
    // Shader Store
    private ShaderStore shaders;

    // Uniform Names
    private Set<String> uniformNames;
    // Uniform Store
    UniformStore uniforms;

    //List of Renderables managed by this ShaderProgram
    private List<Renderable> renderables;

    /**
     * Constructor
     *
     * @param shaders
     *         the common shader store
     */
    ShaderProgram(ShaderStore shaders, UniformStore uniforms) {
        shaderIDs = new HashSet<>();
        uniformNames = new HashSet<>();
        renderables = new ArrayList<>();
        this.shaders = shaders;
        this.uniforms = uniforms;
        ID = GL20.glCreateProgram();
        loadShaders();
        bindAttributes();
        bindUniforms();
        loadRenderables();
        GL20.glLinkProgram(ID);
        GL20.glValidateProgram(ID);
    }

    /**
     * Load a shader for this program
     *
     * @param path
     *         the filepath to the shader
     * @param type
     *         the type of shader
     */
    void loadShader(String path, int type) {
        int sID = shaders.register(path, type);
        shaderIDs.add(sID);
        GL20.glAttachShader(ID, sID);
    }

    /**
     * Bind all shaders in this method
     */
    public abstract void loadShaders();

    /**
     * Bind a uniform variable to this program
     *
     * @param name
     *         the variable name of the uniform
     */
    void bindUniform(String name) {
        uniformNames.add(name);
    }

    /**
     * Bind all uniforms in this method
     */
    public abstract void bindUniforms();


    /**
     * Bind an attribute name to an index in the VAO
     *
     * @param index
     *         VAO index
     * @param name
     *         shader variable name
     */
    void bindAttribute(int index, String name) {
        GL20.glBindAttribLocation(ID, index, name);
    }

    /**
     * Bind all attributes in this method
     */
    public abstract void bindAttributes();

    /**
     * Add a renderable to this Program
     *
     * @param object
     *         the renderable to add
     */
    protected void loadRenderable(Renderable object) {
        renderables.add(object);
    }

    /**
     * Load all renderables
     */
    public abstract void loadRenderables();

    /**
     * Execute this program
     */
    public void run() {
        GL20.glUseProgram(ID);
        uniformNames.forEach((name) -> uniforms.load(ID, name));
        renderables.forEach(Renderable::render);
    }

    /**
     * Terminate this program
     */
    public void stop() {
        GL20.glUseProgram(0);
    }

    /**
     * Clean up loadShaders and remove this program
     */
    public void free() {
        stop();
        shaderIDs.forEach((sID) -> {
            GL20.glDetachShader(ID, sID);
            shaders.unregister(sID);
        });
        GL20.glDeleteProgram(ID);
        renderables.forEach(Renderable::free);
    }

}
