uniform mat4 uMVPMatrix;
attribute vec3 fromPosition;
attribute vec4 fromColor;
varying vec4 aColor;

void main(){
     gl_Position = uMVPMatrix * vec4(fromPosition,1);
     aColor = fromColor;
}