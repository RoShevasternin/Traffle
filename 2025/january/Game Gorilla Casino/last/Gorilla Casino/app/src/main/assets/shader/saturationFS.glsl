#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
varying vec4 v_color;// Колір, переданий із вершинного шейдера

uniform sampler2D u_texture;
uniform float u_saturation[9]; // Масив насиченості для кожного квадрата (0 або 1)
uniform float u_tileSize;      // Розмір одного квадрата (у відсотках текстури: 1.0 / 3.0 для 3х3)

void main() {
    vec4 color = texture2D(u_texture, v_texCoords) * v_color;;

    // Обчислення індексу квадрата
    float tileX = floor(v_texCoords.x / u_tileSize); // Номер колонки (0, 1, 2)
    float tileY = floor(v_texCoords.y / u_tileSize); // Номер рядка (0, 1, 2)
    int tileIndex = int(tileY * 3.0 + tileX);       // Індекс квадрата (0-8)

    // Отримання насиченості для цього квадрата
    float saturation = u_saturation[tileIndex];

    // Розрахунок відтінку сірого
    float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    vec3 grayscale = vec3(gray);

    // Інтерполяція між сірим і кольором на основі насиченості
    vec3 finalColor = mix(grayscale, color.rgb, saturation);

    gl_FragColor = vec4(finalColor, color.a);
}
