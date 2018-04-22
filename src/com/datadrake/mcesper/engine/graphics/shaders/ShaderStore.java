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

package com.datadrake.mcesper.engine.graphics.shaders;

import com.datadrake.mcesper.util.FileUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;
import java.util.Map;

/**
 * ShaderStore manages loadShaders for multiple ShaderPrograms
 */
public class ShaderStore {

    // Map filepaths to shader IDs
    private Map<String, Integer> paths;
    // Keep track of references to shaders
    private Map<Integer, Integer> refs;

    /**
     * Constructor
     */
    public ShaderStore() {
        paths = new HashMap<>();
        refs = new HashMap<>();
    }

    /**
     * Create a new shader if missing
     *
     * @param path
     *         the filepath to the shader
     * @param type
     *         the kind of shader
     *
     * @return the ID of this new shader
     */
    public int register(String path, int type) {
        Integer id = paths.get(path);
        // Compile if missing
        if (id == null) {
            id = compileShader(path, type);
            paths.put(path, id);
        }
        // Update ref count
        refs.merge(id, 1, (curr, next) -> curr + next);
        return id;
    }

    /**
     * Decrement references to a shader by ID, removing if no longer referenced
     *
     * @param id
     *         the ID of the shader
     */
    public void unregister(int id) {
        // Update ref count
        refs.merge(id, 1, (curr, next) -> {
            if (curr == 0) {
                GL20.glDeleteShader(id);
                paths.values().remove(id);
                return null;
            }
            return curr - next;
        });
    }

    /**
     * Clean up shaders before shutting down
     */
    public void free() {
        paths.forEach((k, v) -> {
            if (refs.get(v) > 0) {
                System.err.printf("There are still %d refs to %s\n", refs.get(v), k);
                GL20.glDeleteShader(paths.get(k));
            }
        });
    }

    /**
     * Compile a new shader from a file
     *
     * @param path
     *         the filepath of the shader source
     * @param type
     *         the kind of shader
     *
     * @return the ID of the new shader
     */
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
