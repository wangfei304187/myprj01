#version 330 core

out vec4 FragColor;
in vec2 TexCoord;

uniform sampler2D ourTexture1;
uniform sampler2D ourTexture2;

void main()
{
   gl_FragColor = texture(ourTexture1, TexCoord) + texture(ourTexture2, TexCoord);
}