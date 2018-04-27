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

import com.datadrake.mcesper.engine.data.vbo.FloatVBO;
import com.datadrake.mcesper.engine.data.vbo.UintVBO;
import com.datadrake.mcesper.engine.graphics.Renderable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * The Player object
 */
public class Map implements Renderable {

    private final static int SIZE = 200;

    private final static int VBO_POSITION = 0;
    //private final static int VBO_NORMAL = 1;
    private final static int VBO_INDICES = 2;

    //VAO
    private int vaoID;

    // VBO
    private FloatVBO position;
    //private FloatVBO normal;
    private UintVBO indices;

    /**
     * Constructor
     */
    public Map() {
        vaoID = GL30.glGenVertexArrays();
        bind();
        float positions [] = new float[SIZE*SIZE*3];
        int i = 0;
        for (int z = 0; z < SIZE; z++) {
            for (int y = 0; y < 1; y++ ) {
                for (int x = 0; x < SIZE; x++) {
                    positions[i] = (float)x*0.02f;
                    positions[i+1] = (float)y*0.02f;
                    positions[i+2] = (float)z*0.02f;
                    i += 3;
                }
            }
        }
        position = new FloatVBO(VBO_POSITION, 3, GL15.GL_ARRAY_BUFFER, positions);
        // normal = new FloatVBO(VBO_NORMAL, 3, new float[]{0, 1, 0});
        //indices = new UintVBO(VBO_INDICES, 1, GL15.GL_ELEMENT_ARRAY_BUFFER, new int[]{0,1,2,2,1,3});
        unbind();
    }

    /**
     * Get the player's current position in the world
     *
     * @return the (X,Y,Z) coords of the player
     */
    public float[] getPosition() {
        return position.getLast();
    }

    /**
     * Get the player's current normal vector in the world
     *
     * @return the (X,Y,Z) coords of the player's normal vector
     */
/*
    public float[] getNormal() {
        return normal.getLast();
    }
*/

    /**
     * Get the player's current view direction in the world
     *
     * @return the (X,Y,Z) coords of the player's view vector
     */
/*    public float[] getDirection() {
        return direction.getLast();
    }*/

    public void bind() {
        GL30.glBindVertexArray(vaoID);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void free() {
        GL30.glDeleteVertexArrays(vaoID);
        position.free();
        //normal.free();
        //indices.free();
    }

    public void render() {
        bind();
        position.bind();
        GL20.glEnableVertexAttribArray(VBO_POSITION);
        GL11.glDrawArrays(GL11.GL_POINTS, 0, SIZE*SIZE);
        GL20.glDisableVertexAttribArray(VBO_POSITION);
        position.unbind();
        unbind();
    }

}
