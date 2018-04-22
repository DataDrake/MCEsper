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

package com.datadrake.mcesper.engine.graphics;

import com.datadrake.mcesper.engine.data.vbo.FloatVBO;
import com.datadrake.mcesper.engine.data.vbo.UintVBO;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * The Player object
 */
public class Cube {

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
    public Cube() {
        vaoID = GL30.glGenVertexArrays();
        bind();
        position = new FloatVBO(VBO_POSITION, 3, GL15.GL_ARRAY_BUFFER, new float[]{-0.5f, -0.5f, 0,
                                                                                            0.5f, -0.5f, 0,
                                                                                            -0.5f, 0.5f, 0,
                                                                                            0.5f, 0.5f, 0});
        // normal = new FloatVBO(VBO_NORMAL, 3, new float[]{0, 1, 0});
        indices = new UintVBO(VBO_INDICES, 1, GL15.GL_ELEMENT_ARRAY_BUFFER, new int[]{0,1,2,2,1,3});
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
        indices.free();
    }

    public void render() {
        bind();
        position.bind();
        GL20.glEnableVertexAttribArray(VBO_POSITION);
        indices.bind();
        GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
        indices.unbind();
        GL20.glDisableVertexAttribArray(VBO_POSITION);
        position.unbind();
        unbind();
    }

}
