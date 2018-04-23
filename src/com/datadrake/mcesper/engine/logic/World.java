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
import com.datadrake.mcesper.engine.graphics.WindowManager;
import com.datadrake.mcesper.util.MatrixUtil;
import org.joml.Matrix4f;

public class World {

    private static final float FIELD_OF_VIEW = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;

    private UniformMat4 projection;

    public World(UniformStore uniforms) {
        Matrix4f pMat = MatrixUtil.project(FIELD_OF_VIEW, NEAR_PLANE, FAR_PLANE, WindowManager.getWidth(), WindowManager.getHeight());
        projection = new UniformMat4("projection", pMat);
        uniforms.put("projection", projection);
    }

}
