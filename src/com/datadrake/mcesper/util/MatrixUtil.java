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
    public static float[] transform(float[] translate, float[] rotate, float scale) {
        float[] tform = new float[16];
        Matrix4f raw = new Matrix4f();
        raw.identity();
        raw.translate(translate[0], translate[1], translate[2], raw);
        raw.rotate((float)Math.toRadians(rotate[0]), 1, 0, 0, raw);
        raw.rotate((float)Math.toRadians(rotate[1]), 0, 1, 0, raw);
        raw.rotate((float)Math.toRadians(rotate[2]), 0, 0, 1, raw);
        raw.scale(scale, raw);
        raw.get(tform);
        return tform;
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
    public static float[] project(float fov, float near, float far, int width, int height) {
        float aspect = (float)width / (float)height;
        float sy = (1f / (float)Math.tan(Math.toRadians(fov / 2f))) * aspect;
        float sx = sy / aspect;
        float sz = -((far + near) / (far - near));
        float wz = -((2f * near * far) / (far - near));
        Matrix4f raw = new Matrix4f();
        raw._m00(sx);
        raw._m11(sy);
        raw._m22(sz);
        raw._m23(-1);
        raw._m32(wz);
        float[] proj = new float[16];
        raw.get(proj);
        return proj;
    }

}
