#version 120

#define M_PI 3.1415926535897932384626433832795

uniform sampler2D rend_tex;
varying vec2 texcoord;
uniform int time;

void main() {
    vec4 color = vec4(0);
    color += texture2D(rend_tex, texcoord);
    gl_FragColor = color;
}
