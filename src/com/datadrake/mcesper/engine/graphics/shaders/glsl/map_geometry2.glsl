#version 400 core

layout (points) in;
layout (triangle_strip, max_vertices=24) out;


in vec3 color[];
out vec3 fragColor;

uniform mat4 transform;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 vertices[8];
uniform vec3 normals[6];
uniform vec3 light;

const float ambient = 0.2f;
const float reflection = 0.1f;

uint indices[] = uint[](
    0,2,1,3,
    1,3,7,5,
    7,5,6,4,
    6,4,0,2,
    6,0,7,1,
    4,6,5,7
);

vec3 normalColor(vec3 normal) {
    vec3 lightNormal = normalize(light - gl_Position.xyz);
    vec3 surfaceNormal = normalize((transform * vec4(normal, 0)).xyz);
    vec3 cameraNormal = normalize((inverse(view) * vec4(0,0,0,1)).xyz - gl_Position.xyz);
    float angle = max(dot(lightNormal, surfaceNormal), 0);
    float cameraAngle = reflection * max(dot(reflect(-1*lightNormal,surfaceNormal), cameraNormal), 0);
    return color[0] * (angle + cameraAngle + ambient);
}

void genFace(int face){
    int offset = face*4;
    vec4 basePos = gl_in[0].gl_Position;
    vec3 normal = normals[face];
    vec3 nColor = normalColor(normal+vertices[indices[offset]]);
    mat4 pv = projection*view;
    for ( int i = 0; i < 4; i++) {
        vec3 vertex = vertices[indices[i+offset]];
        vec4 pos = basePos + vec4(vertex, 1);
        fragColor = nColor;
        //fragColor = normalColor(normal+vertex);
        //fragColor = vec3(0,1,0);
        gl_Position = pv * pos;
        EmitVertex();
    }
    EndPrimitive();
}

void main() {
    for(int face = 0; face < 6; face++) {
        genFace(face);
    }
}