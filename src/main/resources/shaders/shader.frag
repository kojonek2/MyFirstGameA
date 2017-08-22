#version 420 core

in vec3 textureCords;

uniform samplerCube textureSampler;

out vec4 out_color;

void main() {
		out_color = texture(textureSampler, textureCords);
}