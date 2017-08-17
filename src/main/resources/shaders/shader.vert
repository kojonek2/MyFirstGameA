#version 400 core

in vec3 position;

out vec3 color;

void main() {
	gl_Position = vec4(position, 1);
	color = vec3(position.x + 0.5, position.y + 0.5, position.z + 0.5);
}