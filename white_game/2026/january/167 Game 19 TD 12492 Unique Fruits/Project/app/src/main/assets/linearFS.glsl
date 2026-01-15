#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_texture;

//uniform vec3 u_startColor;// Колір початку градієнта
//uniform vec3 u_endColor;// Колір кінця градієнта
//uniform vec2 u_center;// Центр градієнта (в нормалізованих координатах)
//uniform float u_radius;// Радіус градієнта
//uniform float u_time;// Час для анімації
//uniform float u_cycleTime;// Тривалість одного циклу анімації в секундах
//uniform bool u_animate;// Чи потрібно анімувати градієнт

uniform vec2 u_center;      // Центр гіпнотичного ефекту
uniform float u_time;       // Час для анімації
uniform float u_cycleTime;  // Тривалість одного циклу анімації в секундах
uniform int u_numRings;     // Кількість кілець


const float PI     = 3.14159;
const float TWO_PI = 2.0 * PI;

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoord0);
    vec2 uv = v_texCoord0 - u_center;

    // Обчислення відстані від центру
    float distance = length(uv);

    // Нормалізація часу для циклу анімації
    float normalizedTime = mod(u_time, u_cycleTime) / u_cycleTime;

    // Обчислення радіусів кілець
    float ringRadius = 0.5 * sin(distance * float(u_numRings) * TWO_PI + normalizedTime * TWO_PI) + 0.5;

    // Визначення кольору кільця
    vec3 ringColor = mix(vec3(0.0, 1.0, 0.0), vec3(0.0, 0.0, 1.0), ringRadius);

    // Застосування кольору текстури та кольору кільця
    vec4 resultColor = vec4(ringColor, texColor.a);

    gl_FragColor = resultColor * v_color;

}