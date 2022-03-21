#!/usr/bin/env python
import signal
import sys

running = True

def signal_handler(sig, frame):
    global running
    print('\nYou pressed Ctrl+C!')
    running = False

signal.signal(signal.SIGINT, signal_handler)

id = int(input("start id: "))

while running:
    try:
        class_name = input("class name: ")
        texture_name = input("texture: ")
        name = input("name: ")
    except EOFError:
        break

    filename = class_name + "Tile.java"
    with open(filename, "w") as f:
        f.write("package tiles;\n\n")
        f.write("import processing.core.PApplet;\n")
        f.write("import util.TextureManager;\n\n")
        f.write("public class " + class_name + "Tile extends Tile {\n")
        f.write("    public static final int ID = " + str(id) + ";\n")
        f.write("    public " + class_name + "Tile (PApplet parent) {\n")
        f.write("        super(parent);\n")
        f.write("        this.texture = TextureManager.getInstance().getTexture(\"" + texture_name + "\");\n")
        f.write("    }\n")
        f.write("    \n")
        f.write("    public String getName() {\n")
        f.write("        return \"" + name + "\";\n")
        f.write("    }\n")
        f.write("}")

    id += 1