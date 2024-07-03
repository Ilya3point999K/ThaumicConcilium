#version 120

varying vec2 texcoord;
uniform int time;

const float amplitude = 0.05;
const float frequency = 0.00001;
const float PI = 3.14159;


float rand(vec2 co){
    return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
}

void main()
{
    vec4 vert = gl_Vertex;
    vert.xyz += cos(time*vec3(6.0, 6.0, 7.0) + vert.xyz*10.0)*0.02;
    gl_Position = gl_ModelViewProjectionMatrix * vert;
    texcoord = vec2(gl_MultiTexCoord0);
}