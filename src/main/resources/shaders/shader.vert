#version 400 core

in vec3 position;

out vec3 color;

void main() {
	gl_Position = vec4(position, 0.0);
	color = vec3(1.0, 0.0, 1.0);
}