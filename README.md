#LOG8430 - TP4 - SOA, REST, Infonuagique, Architectures Multi-niveaux pour le traitement de mégadonnées.

Ce README détaille les étapes pour pouvoir mettre en place le service distribué.

##Marche à suivre

###Pré-requis

Il est important de noter que chaque étape doit être effectuée dans une machine virtuelle différente.
Dans chacune de ces machines virtuelles, il est nécessaire de cloner le repository git du TP afin de récupérer l'ensemble des fichiers de configuration:
```
git clone //Adresse git
```

###Installation de Spark

Ajouter winutils.exe à votre variable d'environnement :

- Download [winutils.exe](http://public-repo-1.hortonworks.com/hdp-win-alpha/winutils.exe)
- Create folder, say C:\winutils\bin
- Copy winutils.exe inside C:\winutils\bin
- Set environment variable HADOOP_HOME to C:\winutils

Lancer spark :

- Créez un fichier test.txt  dans le dossier /spark avec le texte à analyser
- Changez les arguments à passer dans main (ici "test.txt")
- Project -> Clean
- Maven -> build "clean install"
- WordCount -> run as java application
- La sortie se trouve dans le dossier output

### Lancement du service REST

#### Dépendances du service
- Node.js et npm
- Java
- Apache Spark

#### Installation des dépendances

Pour installer les dépendances du service web, un script shell est fourni dans le dossier setup. Pour le lancer, se placer à la racine du projet et exécuter les commandes suivantes :
```
chmod +x setup/service/setup.sh
./setup/service/setup.sh
```

#### Lancement du service REST
Pour démarrer le service, se placer dans le répertoire service depuis la racine du projet puis exécuter les commandes suivantes:
```
npm start
```
Une fois le service démarré, les requêtes entrantes seront affichées dans le terminal.

### Lancement du client et des tests

Pour lancer les tests unitaires, se placer dans 

