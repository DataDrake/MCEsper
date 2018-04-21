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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * The Player object
 */
public class Player {

    private final static float PLAYER_HEIGHT = 0.1f;
    private final static int VBO_POSITION = 0;
    private final static int VBO_NORMAL = 1;
    private final static int VBO_DIRECTION = 2;

    //VAO
    private int vaoID;

    // VBO
    private FloatVBO position;
    private FloatVBO normal;
    private FloatVBO direction;

    /**
     * Constructor
     */
    public Player() {
        vaoID = GL30.glGenVertexArrays();
        bind();
        position = new FloatVBO(VBO_POSITION, 3, new float[]{0, 0, 0});
        normal = new FloatVBO(VBO_NORMAL, 3, new float[]{0, 1, 0});
        direction = new FloatVBO(VBO_DIRECTION, 3, new float[]{0, 0, -1});
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
    public float[] getNormal() {
        return normal.getLast();
    }

    /**
     * Get the player's current view direction in the world
     *
     * @return the (X,Y,Z) coords of the player's view vector
     */
    public float[] getDirection() {
        return direction.getLast();
    }

    public void bind() {
        GL30.glBindVertexArray(vaoID);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void free() {
        GL30.glDeleteVertexArrays(vaoID);
        position.free();
        normal.free();
        direction.free();
    }

    public void render() {
        bind();
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_POINT, 0, 1);
        GL20.glDisableVertexAttribArray(0);
        unbind();
    }

}
