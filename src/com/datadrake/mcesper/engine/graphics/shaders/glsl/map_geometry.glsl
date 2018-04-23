#version 400 core

layout (points) in;
layout (triangle_strip, max_vertices=36) out;


in vec3 color[];
out vec3 fragColor;

uniform mat4 transform;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 vertices[8];

uint indices[14] = uint[14](0,1,2,3,4,1,5,0,6,2,7,4,6,5);

void main() {

    vec4 basePos = gl_in[0].gl_Position;
    for(int i = 0; i < 14; i++) {
        gl_Position = basePos + vec4(vertices[indices[i]], 1);
        gl_Position = projection * view * transform * gl_Position;
        fragColor = color[0];
        EmitVertex();
    }
    EndPrimitive();
}