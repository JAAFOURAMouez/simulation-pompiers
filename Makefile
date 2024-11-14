# Répertoires de source et de sortie
SRC_DIRS = src/gui src/carte src/robot src/simulateur src/tests
OUT_DIR = bin
JAR_FILE = pompiers_sauveurs_de_vie.jar

# Cible par défaut
all: compile

# Cible pour la compilation des fichiers Java
compile:
	@echo "Compilation des fichiers Java..."
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestSimulateur.java

# Default target for running TestSimulateur with a specified map
run:
	@echo "Exécution de TestSimulateur sur $(MAP) ..."
	@java -classpath bin:lib/gui.jar tests.TestSimulateur maps/$(MAP)

# Clean target to remove compiled files (if needed)
clean:
	@echo "Cleaning compiled files..."
	@rm -rf bin/*.class

# Cible pour ajouter, commettre et pousser sur Git
push:
	@echo "Poussée des modifications vers le dépôt Git..."
	@git add .
	@git commit -m "Commit des modifications"
	@git push

# Cible pour créer le fichier JAR
jar:
	@echo "Création du fichier JAR..."
	@jar cvf $(JAR_FILE) -C $(OUT_DIR) .

# Cible pour recompiler et exécuter le programme
rebuild: clean all run
