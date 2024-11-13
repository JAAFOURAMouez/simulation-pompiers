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
# Cible pour exécuter TestSimulateur
run_spiral:
	@echo "Exécution de TestSimulateur sur spiral.map ... "
	@java -classpath bin:lib/gui.jar tests.TestSimulateur maps/spiral.map
run_hell:
	@echo "Exécution de TestSimulateur sur hell.map ... "
	@java -classpath bin:lib/gui.jar tests.TestSimulateur maps/hell.map
run_carteSujet:
	@echo "Exécution de TestSimulateur sur hell.map ... "
	@java -classpath bin:lib/gui.jar tests.TestSimulateur maps/carteSujet.map
# Cible pour nettoyer les fichiers compilés
clean:
	@echo "Nettoyage des fichiers compilés..."
	@rm -rf $(OUT_DIR) $(JAR_FILE)

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
