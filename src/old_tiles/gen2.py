import os

from os import listdir
from os.path import isfile, join

onlyfiles = [f for f in listdir(os.getcwd()) if isfile(join(os.getcwd(), f))]
exclude = ["TileFactory.java", "TileFactoryBackup.java", "gen.py", "gen2.py", "Tile.java"]

for file in onlyfiles:
    if file in exclude:
        continue
    print("if (name.equalsIgnoreCase(\"" + os.path.splitext(file)[0] + "\")) {")
    print("    return new " + os.path.splitext(file)[0] + "(parent);\n}")

print()
print()

for file in onlyfiles:
    if file in exclude:
        continue
    print("if (id == " + os.path.splitext(file)[0] + ".ID) {")
    print("    return new " + os.path.splitext(file)[0] + "(parent);\n}")
