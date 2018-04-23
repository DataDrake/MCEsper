#version 400 core

layout (points) in;
layout (triangle_strip, max_vertices=36) out;


in vec3 color[];
out vec3 fragColor;

uniform mat4 transform;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 vertices[8];
uniform vec3 light;

uint indices[14] = uint[14](0,1,2,3,4,1,5,0,6,2,7,4,6,5);

void main() {

    vec4 basePos = gl_in[0].gl_Position;
    for(int i = 0; i < 14; i++) {
        vec3 vertex = vertices[indices[i]];
        gl_Position = basePos + vec4(vertex, 1);
        vec3 lightNormal = normalize(light - gl_Position.xyz);
        vec3 surfaceNormal = normalize((transform * vec4(vertex, 0)).xyz);
        float angle = max(dot(lightNormal, surfaceNormal), 0);
        fragColor = color[0] * (angle + 0.2f);

        gl_Position = projection * view * transform * gl_Position;

        EmitVertex();
    }
    EndPrimitive();
}