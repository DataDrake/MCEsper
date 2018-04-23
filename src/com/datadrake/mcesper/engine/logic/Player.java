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

package com.datadrake.mcesper.engine.logic;

import com.datadrake.mcesper.engine.data.UniformStore;
import com.datadrake.mcesper.engine.data.uniform.UniformMat4;
import com.datadrake.mcesper.util.MatrixUtil;
/**
 * The Player object
 */
public class Player {

    private final static float PLAYER_HEIGHT = 0.1f;

    private float[] position;
    private float[] normal;
    private float[] direction;

    private UniformMat4 transform;
    private UniformMat4 view;


    /**
     * Constructor
     */
    public Player(UniformStore uniforms) {
        position = new float[]{0, 0, 0};
        normal = new float[]{0, 1, 0};
        direction = new float[]{0, 0, -1};

        transform = new UniformMat4("transform", MatrixUtil.transform(new float[]{0, 0, -2}, new float[]{0, 0, 0}, 1f));
        uniforms.put("transform", transform);
        view = new UniformMat4("view", MatrixUtil.view(position, normal, direction));
        uniforms.put("view", view);
    }

    /**
     * Get the player's current position in the world
     *
     * @return the (X,Y,Z) coords of the player
     */
    public float[] getPosition() {
        return position;
    }

    /**
     * Get the player's current normal vector in the world
     *
     * @return the (X,Y,Z) coords of the player's normal vector
     */
    public float[] getNormal() {
        return normal;
    }

    /**
     * Get the player's current view direction in the world
     *
     * @return the (X,Y,Z) coords of the player's view vector
     */
    public float[] getDirection() {
        return direction;
    }

}
