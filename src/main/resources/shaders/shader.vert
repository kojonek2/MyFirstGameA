#version 420 core

in vec3 position;
in vec2 texture_cord;

out vec2 pass_texture_cord;

uniform mat4 transformationMatrix;

void main() {
	gl_Position = vec4(position, 1);
	pass_texture_cord = texture_cord;
}