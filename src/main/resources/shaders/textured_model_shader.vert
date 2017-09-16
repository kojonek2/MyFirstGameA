#version 420 core

in vec3 position;
in vec2 textureCords;

out vec2 pass_textureCords;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

void main() {
	gl_Position = projection_matrix * view_matrix * transformation_matrix * vec4(position, 1);
	pass_textureCords = textureCords;
}