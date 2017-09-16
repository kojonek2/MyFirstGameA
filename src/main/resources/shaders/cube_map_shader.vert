#version 420 core

in vec3 position;

out vec3 textureCords;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

void main() {
	gl_Position = projection_matrix * view_matrix * transformation_matrix * vec4(position, 1);
	textureCords = vec3(position.x, position.y - 0.5, position.z);
}