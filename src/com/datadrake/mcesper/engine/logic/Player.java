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
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.Vector;

/**
 * The Player object
 */
public class Player {

    public final static int STRAFE_FORWARD = 0;
    public final static int STRAFE_BACK = 1;
    public final static int STRAFE_LEFT = 2;
    public final static int STRAFE_RIGHT = 3;


    private final static float PLAYER_HEIGHT = 3f;
    
    private Vector3f position;
    private Vector3f front;
    private Vector3f up;
    private Vector2f rotation;

    private UniformMat4 transform;
    private UniformMat4 view;

    private double oldTime;


    /**
     * Constructor
     */
    public Player(UniformStore uniforms) {

        position = new Vector3f().set(0, 0, 3);
        front = new Vector3f().set(0,0,-1f);
        up = new Vector3f().set(0,1,0);

        rotation = new Vector2f().set(-90,0);
        setFront();

        transform = new UniformMat4("transform", MatrixUtil.transform(new float[]{0, 0, -2}, new float[]{0, 0, 0}, 1f));
        uniforms.put("transform", transform);
        view = new UniformMat4("view", MatrixUtil.view(position, front, up));
        uniforms.put("view", view);

        oldTime = GLFW.glfwGetTime();
        
    }

    private void setFront() {
        front.set(
                (float)(Math.cos(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x))),
                (float)(Math.sin(Math.toRadians(rotation.y))),
                (float)(Math.cos(Math.toRadians(rotation.y)) * Math.sin(Math.toRadians(rotation.x)))
        ).normalize();
    }


    public void move(int dir) {
        double newTime = GLFW.glfwGetTime();
        float amount = 1f *(float) (newTime - oldTime);
        oldTime = newTime;
        if (amount > 0.05f) { amount = 0.05f; }
        Vector3f dz = new Vector3f();
        front.mul(amount,dz);
        Vector3f dy = new Vector3f();
        front.cross(up,dy);
        dy.mul(amount);

        switch (dir) {
        case STRAFE_FORWARD:
            position.add(dz);
            break;
        case STRAFE_BACK:
            position.sub(dz);
            break;
        case STRAFE_LEFT:
            position.sub(dy);
            break;
        case STRAFE_RIGHT:
            position.add(dy);
            break;
        }
        view.update(MatrixUtil.view(position, front, up));
    }

    public void pivot(float dx, float dy) {
        rotation.add(dx*0.5f,dy*0.5f);
        if( rotation.y > 60f ) {
            rotation.y = 60f;
        }
        if (rotation.y < -60f) {
            rotation.y = -60f;
        }
        //rotation.x = rotation.x % 360.0f;
        setFront();
        view.update(MatrixUtil.view(position, front, up));
    }

    /**
     * Get the player's current position in the world
     *
     * @return the (X,Y,Z) coords of the player
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Get the player's current normal vector in the world
     *
     * @return the (X,Y,Z) coords of the player's normal vector
     */
    public Vector3f getNormal() {
        return up;
    }

    /**
     * Get the player's current view rotation in the world
     *
     * @return the (X,Y,Z) coords of the player's view vector
     */
    public Vector2f getRotation() {
        return rotation;
    }

}
