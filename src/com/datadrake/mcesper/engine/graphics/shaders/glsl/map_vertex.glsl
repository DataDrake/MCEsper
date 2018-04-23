#version 400 core

in vec3 position;

out vec3 color;

uniform mat4 transform;
uniform mat4 projection;

void main() {
    gl_Position = projection * transform * vec4(position, 1.0);
    color = vec3(0,0,1);
}
