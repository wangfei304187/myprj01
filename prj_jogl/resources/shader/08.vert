#version 330 core

layout (location = 0) in vec3 aPos;
//layout (location = 1) in vec2 aTexCoord;

uniform mat4 mvp;

//out vec2 TexCoord;
out vec3 fragPosition;

void main()
{
	gl_Position = mvp * vec4(aPos.x, aPos.y, aPos.z, 1.0);
	fragPosition = aPos;
}