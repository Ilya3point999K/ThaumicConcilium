#version 120

#define M_PI 3.1415926535897932384626433832795

uniform sampler2D rend_tex;
uniform int time;

uniform int progress;
uniform int index;
uniform int next;
uniform int[6] indexes;
uniform int[6] nexts;

vec4 colors[6] = vec4[](
    vec4(1.,0.988,1.,1.),
    vec4(0.42,0.42,0.42,1.),
    vec4(1.,0.,0.,1.),
    vec4(0.188,0.98,0.973,1.),
    vec4(0.188,1.,0.,1.),
    vec4(0.965,1.,0.,1.)
);

void main() {
    float div = 1.2;
    float speed = 60.0;
    vec4 color = vec4(0);
    vec2 texcoord = vec2(gl_TexCoord[0]);
    color += texture2D(rend_tex, texcoord);


    if(color.b == 1.0){
        color = mix(colors[indexes[0]], colors[nexts[0]], progress / speed) / div;
    }
    else if(color.b == 254.0 / 255.0){
        color = mix(colors[indexes[1]], colors[nexts[1]], progress / speed) / div;
    }
    else if(color.b == 253.0 / 255.0){
        color = mix(colors[indexes[2]], colors[nexts[2]], progress / speed) / div;
    }
    else if(color.b == 252.0 / 255.0){
        color = mix(colors[indexes[3]], colors[nexts[3]], progress / speed) / div;
    }
    else if(color.b == 251.0 / 255.0){
        color = mix(colors[indexes[4]], colors[nexts[4]], progress / speed) / div;
    }
    else if(color.b == 250.0 / 255.0){
        color = mix(colors[indexes[5]], colors[nexts[5]], progress / speed) / div;
    }

    /*
    if(color.r == 1.0){
        color = mix(colors[index], colors[next], progress / 60.0) / 1.2;
    }
    */
    gl_FragColor = color;
}