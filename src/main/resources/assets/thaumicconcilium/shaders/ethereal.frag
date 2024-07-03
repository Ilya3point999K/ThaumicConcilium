#version 120
uniform sampler2D rend_tex;
uniform int time;
uniform int progress;

const vec4 blue = vec4(0.0F, 191.0F / 255.0F, 1.0F, 0.5F);

void main() {
    float div = 1.2;
    float speed = 150.0;
    vec4 color = vec4(0);
    vec2 texcoord = vec2(gl_TexCoord[0]);
    color += texture2D(rend_tex, texcoord);

    color = mix(color, blue, progress / speed);

    gl_FragColor = color;
}
