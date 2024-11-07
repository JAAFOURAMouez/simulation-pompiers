# Répertoires de source et de sortie
SRC_DIRS = src/gui src/carte src/robot src/simulateur src/tests
OUT_DIR = bin
JAR_FILE = pompiers_sauveurs_de_vie.jar

# Cible par défaut
all: compile

# Cible pour la compilation des fichiers Java
compile:
	@echo "Compilation des fichiers Java..."
	@mkdir -p $(OUT_DIR)
	@find $(SRC_DIRS) -name "*.java" | xargs javac -d $(OUT_DIR) -cp src

# Cible pour exécuter TestSimulateur
run:
	@echo "Exécution de TestSimulateur..."
	@java -cp $(OUT_DIR) tests.TestSimulateur

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
