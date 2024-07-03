#version 120
uniform sampler2D rend_tex;
uniform int time;
uniform int progress;

const vec4 white = vec4(0.7F, 0.7F, 0.7F, 1.0F);

void main() {
    float div = 1.2;
    float speed = 110.0;
    vec4 color = vec4(0);
    vec2 texcoord = vec2(gl_TexCoord[0]);
    color += texture2D(rend_tex, texcoord);

    color.xyz = mix(color.xyz, white.xyz, progress / speed);

    gl_FragColor = color;
}
