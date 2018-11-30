#LOG8430 - TP4 - SOA, REST, Infonuagique, Architectures Multi-niveaux pour le traitement de mégadonnées.

Ce README détaille les étapes pour pouvoir mettre en place le service distribué.



##Marche à suivre

###Pré-requis

Il est important de noter que chaque étape doit être effectuée dans une machine virtuelle différente.
Dans chacune de ces machines virtuelles, il est nécessaire de cloner le repository git du TP afin de récupérer l'ensemble des fichiers de configuration:
```
git clone https://github.com/brigondaud/JavaSOA.git
```

#### Arborescence générée
```
.
├── client
│   ├── nb-configuration.xml
│   ├── pom.xml
│   ├── src # Sources du client
│   └── test # Ensemble des tests unitaires du service REST
├── service
│   ├── app.js
│   ├── bin
│   ├── config.json
│   ├── gulpfile.js
│   ├── lib
│   ├── package.json
│   └── routes
├── setup
│   ├── service # Fichiers d'initialisation du service REST
│   ├── spark
└── sparkJob
    ├── job-0.1.jar
    ├── output
    ├── pom.xml
    ├── src
    └── test.txt
```

A faire manuellement pour spark:
conf/slaves (ajouter toutes les ip du slaves sur ce fichier dans le master)
mettre dans le fichier conf/spark-env.sh: ajouter SPARK_MASTER_HOST=<@IP-Master>

### Lancement du service REST

#### Dépendances du service
- Apache Spark
- Node.js et npm
- Java

#### Installation des dépendances

Dans une nouvelle machine virtuelle, répeter l'étape précédente pour installer Apache Spark.
Pour installer le reste des dépendances du service web, un script shell est fourni dans le dossier setup. Pour le lancer, se placer à la racine du projet et exécuter les commandes suivantes :
```
chmod +x setup/service/setup.sh
./setup/service/setup.sh
```

#### Démarrer le service
Pour démarrer le service, se placer dans le répertoire service présent à la racine du projet puis exécuter la commande suivante :
```
npm start
```
Une fois le service démarré, les requêtes entrantes et leur code de réponse seront affichées dans le terminal.

### Lancement du client et des tests

Une fois les étapes précédentes effectuées, les tests unitaires peuvent-être lancés en se plaçant dans le répertoire client, présent à la racine du projet, et en exécutant depuis un terminal la commande suivante :
```
mvn test
```
