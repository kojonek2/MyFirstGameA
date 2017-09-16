#version 420 core

in vec2 pass_textureCords;

uniform sampler2D textureSampler;

out vec4 out_color;

void main() {
		out_color = texture(textureSampler, pass_textureCords);
}