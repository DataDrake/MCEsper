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

import com.datadrake.util.FileUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * ShaderProgram manages a single sequence of shaders
 */
public class ShaderProgram {
    private int ID;
    private int vertexID;
    private int fragmentID;

    public ShaderProgram(String vertexPath, String fragmentPath) {
        vertexID = compileShader(vertexPath,GL20.GL_VERTEX_SHADER);
        fragmentID = compileShader(fragmentPath,GL20.GL_FRAGMENT_SHADER);
        ID = GL20.glCreateProgram();
        GL20.glAttachShader(ID,vertexID);
        GL20.glAttachShader(ID,fragmentID);
        GL20.glLinkProgram(ID);
        GL20.glValidateProgram(ID);
    }

    public void bind(int index, String name) {
        GL20.glBindAttribLocation(ID, index, name);
    }

    public void run() {
        GL20.glUseProgram(ID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void free() {
        stop();
        GL20.glDetachShader(ID, vertexID);
        GL20.glDetachShader(ID, fragmentID);
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
        GL20.glDeleteProgram(ID);
    }

    private static int compileShader(String path, int type) {
        String src = FileUtil.readAsString(path);
        int id = GL20.glCreateShader(type);
        GL20.glShaderSource(id, src);
        GL20.glCompileShader(id);
        if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.printf("Failed to compile shader '%s'\n", path);
            System.err.println(GL20.glGetShaderInfoLog(id, 500));
        }
        return id;
    }
}
