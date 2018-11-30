# LOG8430 - TP4 - SOA, REST, Infonuagique, Architectures Multi-niveaux pour le traitement de mégadonnées.

Ce README détaille les étapes pour pouvoir mettre en place le service distribué.



## Marche à suivre

### Pré-requis

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

Pour installer les dépendances du service web, se placer à la racine du projet et exécuter les commandes suivantes :
```
chmod +x setup/spark/setup.sh
./setup/spark/setup.sh
source ~/.bashrc
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

# Installation de spark
- Créer 3 machines virtuelles sur Virtual Box, Linux ubuntu 64-bit. La taille de la mémoire doit être plus grande que 1Go pour chaque machine.
    - master
    - slave01
    - slave01
- Configurer ces 3 machines:
    - Installer ubuntu server
    - Sous l'onglet réseau, ajouter une Carte 2 "Réseau privé hôte" (il faut avoir préalablement créé un réseau hôte dans les paramètres de VirtualBox)
- Démarrer les 3 machines et suivre les instruction d'installation, en précisant "master", "slave01" et "slave02" dans les champs "Your server's Name".
- Lorsque les machines sont démarrées, dans chacune d'elles:
    - sudo apt-get update
    - sudo apt-get install git
    - git clone http://github.com/brigondaud/JavaSOA
Lancer le script de setup spark :
    - Changer les permissions chmod +x ./JavaSOA/setup/spark/setup.sh
    - Retourner à la racine pour lancer le setup !
    - Exécuter le setup: ./JavaSOA/setup/spark/setup.sh (le setup peur prendre plusieurs minutes)
    - Nous avons remarqué que si il y a une coupure internet, certains paquets ne sont pas téléchargés correctement. Il faut donc lancer le script ./setup.sh jusqu'à ce qu'il s'exécute sans rien changé.
    - Il faut ensuite ajouter manuellement les adresses IP des différentes machines comme suit :
        - Changer les permissions pour lire : sudo chmod +x ~/spark-2.4.0-bin-hadoop2.7/conf/spark-env
        - Dans le fichier ~/spark-2.4.0-bin-hadoop2.7/conf/spark-env ajouter la ligne: SPARK_MASTER_HOST=<ADDRESSE_IP_MASTER>