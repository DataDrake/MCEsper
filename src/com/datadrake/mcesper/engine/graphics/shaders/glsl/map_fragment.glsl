#version 400 core

in vec3 fragColor;
in vec3 normal;

out vec4 out_Color;

void main() {
    out_Color = vec4(fragColor,1.0);
}
