#version 120

#define M_PI 3.1415926535897932384626433832795

uniform sampler2D rend_tex;
uniform int time;
uniform int progress;

const vec4 black = vec4(0.1F, 0.1F, 0.1F, 1.0F);
const vec4 purple = vec4(0.5F, 0.0F, 0.5F, 1.0F);

void main() {
    float div = 1.2;
    float speed = 60.0;
    vec4 color = vec4(0);
    vec2 texcoord = vec2(gl_TexCoord[0]);
    color += texture2D(rend_tex, texcoord);


    if(color.b == 0.0){
        color = mix(black, purple, progress / speed) / div;
    }

    gl_FragColor = color;
}
