#version 400 core

in vec3 position;

uniform mat4 projectionMatrix;

out vec3 color;

void main() {
	gl_Position = vec4(position, 1) * projectionMatrix;
	color = vec3(1.0, 1.0, 1.0);
}