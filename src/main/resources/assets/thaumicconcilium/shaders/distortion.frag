#version 120

uniform sampler2D texture;
uniform int time;

const float distortionFactor = 0.5;
const float riseFactor = 0.2;

void main() {
    vec4 color = vec4(0.9, 0.9, 0.9, 0.9);
    vec2 distortionMapCoordinate = gl_TexCoord[0].st;
    distortionMapCoordinate.t -= time * riseFactor / 10.0;

    vec4 distortionMapValue = texture2D(texture, distortionMapCoordinate);

    vec2 distortionPositionOffset = distortionMapValue.xy;
    distortionPositionOffset -= vec2(0.5f, 0.5f);
    distortionPositionOffset *= 2.f;

    distortionPositionOffset *= distortionFactor;

    vec2 distortionUnused = distortionMapValue.zw;

    distortionPositionOffset *= (1.f - gl_TexCoord[0].t);

    vec2 distortedTextureCoordinate = gl_TexCoord[0].st + distortionPositionOffset;
    //color.rgb *= sin(time);

    gl_FragColor = color * texture2D(texture, distortedTextureCoordinate);

}