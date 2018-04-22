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

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FileUtil provides useful functions for file manipulation
 */
public class FileUtil {

    /**
     * Read a file in as a string
     *
     * @param path
     *         the location of the file
     *
     * @return contents as a string
     */
    public static String readAsString(String path) {
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return result;
    }

}
