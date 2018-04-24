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

package com.datadrake.mcesper.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Vector;

/**
 * MatrixUtil is a collection of useful matrix operations
 */
public class MatrixUtil {

    /**
     * Generate a transform matrix
     *
     * @param translate
     *         point to translate to
     * @param rotate
     *         amounts to rotate about axes
     * @param scale
     *         amout to scale by
     *
     * @return the matrix
     */
    public static Matrix4f transform(float[] translate, float[] rotate, float scale) {
        Matrix4f raw = new Matrix4f();
        raw.identity();
        //raw.translate(translate[0], translate[1], translate[2]);
        //raw.rotate((float)Math.toRadians(rotate[0]), 1, 0, 0);
        //raw.rotate((float)Math.toRadians(rotate[1]), 0, 1, 0);
        //raw.rotate((float)Math.toRadians(rotate[2]), 0, 0, 1);
        raw.scale(scale);
        return raw;
    }

    /**
     * Generate a projection matrix
     *
     * @param fov
     *         the field-of-view
     * @param near
     *         the near plane
     * @param far
     *         the far plane
     * @param width
     *         window width
     * @param height
     *         window height
     *
     * @return the matrix
     */
    public static Matrix4f project(float fov, float near, float far, int width, int height) {
        float aspect = (float)width / (float)height;
        Matrix4f raw = new Matrix4f();
        raw.perspective((float)Math.toRadians(fov), aspect, near, far);
        return raw;
    }

    public static Matrix4f view(Vector3f position, Vector3f front, Vector3f up) {
        Vector3f center = new Vector3f();
        position.add(front, center);
        Matrix4f raw = new Matrix4f();
        raw.lookAt(position, center, up, raw);
        return raw;
    }

}
