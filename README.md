# LOG8430 - TP4 - SOA, REST, Infonuagique, Architectures Multi-niveaux pour le traitement de mégadonnées.

Ce README détaille les étapes pour pouvoir mettre en place le service de facture distribué.


## Prise en main

### Configuration des machines virtuelles

#### Création d'une machine virtuelle

Pour instancier une machine virtuelle, sous Virtual-Box:
- A la création d'une machine: créer deux interfaces réseau: la première ayant pour mode d'accès Réseau Privé hôte, la deuxième ayant pour mode d'accès NAT.
- Utiliser Ubuntu Server 18.04.1 LTS
- Durant l'étape "Profile Setup" de l'installation du système, remplir tous les champs avec la valeur "ubuntu"
- A la fin de l'installation du système, le redémarrer et exécuter la commande (depuis le répertoire home):

```
git clone https://github.com/brigondaud/JavaSOA.git
```

Cette commande génère l'arborescence suivante:

```
TODO: génerer l'arborescence
```

#### Installation de Spark

Puisque toutes les machines virtuelles (exceptée le client) doivent supporter [Apache Spark](https://spark.apache.org/), lancer son installation en exécutant:

```
chmod +x ~/JavaSOA/setup/spark/setup.sh
~/JavaSOA/setup/spark/setup.sh
source .bashrc
```

**Note**: Le mot de passe administrateur est "ubuntu". Lors de l'installation de Spark, vérifier qu'aucun verrou n'est pris lors de l'installation des paquets (le système peut interférer avec cette opération s'il exécute des tâches en fond). Cette étape peut prendre du temps.

**Note**: Une fois une machine instanciée, elle peut être copiée pour réaliser les différentes installations des éléments du système.

__Attention__: Vérifier de réinitialiser l'adresse MAC de toutes les cartes réseau lors de la copie de la machine virtuelle et réaliser un clone intégral.

**Note**: Dans la suite de ce README, les références aux adresses IP désignent celles qui respectent l'adressage CIDR suivant:

```
192.168.56.0/24
ou
192.186.0.0/16
```

### Configuration d'un Master Spark.

Dans une machine virtuelle clonée, remplacer dans le fichier `~/spark-2.4.0-bin-hadoop2.7/conf/spark-env.sh` la ligne (en éditant le fichier en `sudo` si nécessaire):

```
#SPARK_MASTER_HOST=<MASTER_IP_ADDRESS>
```
En spécifiant l'adresse IP de la machine (en décommentant la ligne).

Cette étape doit être répétée dans la configuration des machines virtuelles workers/slaves Spark.

Il est nécessaire de lister les machines slaves dans le fichier `~/spark-2.4.0-bin-hadoop2.7/conf/slaves` en écrivant chacune des adresses IP des slaves sur une ligne séparée:

```
192.168.56.xxx
192.168.56.yyy
(...)
```

### Configuration du service web REST

La machine virtuelle exécutant le service web doit posséder une installation de Spark (effectuée dans la section Installation de Spark).
Pour installer les dépendances du service web, se placer à la racine du projet et exécuter les commandes suivantes :
```
chmod +x setup/service/setup.sh
./setup/service/setup.sh
```

Modifier le fichier `~/JavaSOA/service/config.json`, le champ `ipMS` doit correspondre à l'adresse IP de la machine virtuelle Master Spark.

Modifier le fichier `~/spark-2.4.0-bin-hadoop2.7/conf/spark-defaults.conf` en décommentant insérant l'adresse IP du service web et en décommentant la ligne suivante:

```
#spark.driver.host <Web service IP>
```

## Déploiement

### Déploiement du service web

Pour déployer le service web, exécuter:

```
cd ~/JavaSOA/service
npm start
```

### Déploiement de la grappe Spark

Pour déployer la grape Spark, exécuter sur le noeud Master:

```
~/spark-2.4.0-bin-hadoop2.7/sbin/start-all.sh
```

**Note**: Le mot de passe de chacune des machines virtuelles Slave est "ubuntu". Il doit être entré pour chacune des machines slave.

### Déploiement de MongoDB

Le déploiement de MongoDB est effectué à l'aide du service [mLab](https://mlab.com/). Il permet de déployer gratuitement une base de données NoSQL orientée Document gratuitement (jusqu'à 500MB de données). La configuration nécessaire pour exécuter des requêtes sur cette base de données est déjà fournie dans le fichier `~/JavaSOA/service/config.json`.

**Note**(facultatif): Il est possible de déployer une base de données MongoDB localement sur une machine virtuelle en exécutant:
```
chmod +x ~/JavaSOA/setup/mongodb/setup.sh
~/JavaSOA/setup/mongodb/setup.sh
```
Il faudra cependant éditer le fichier de configuration du service web (`~/JavaSOA/service/config.json`) sur la machine appropiée.

## Exécution des tests

Une fois les étapes précédentes effectuées, les tests unitaires peuvent-être lancés en se plaçant dans le répertoire client, présent à la racine du projet, et en exécutant depuis un terminal la commande suivante :
```
mvn test
```

TODO

## Développé avec

* [Apache Maven](https://maven.apache.org/)
* [JUnit](https://junit.org/junit4/)
* [Apache Spark](https://spark.apache.org/)
* [NodeJS](https://nodejs.org/)
* [ShellJs](https://github.com/shelljs/shelljs)
* [MongoDB](https://www.mongodb.com/)

## Auteurs

* **Alice Breton (1966869)**
* **Laora Heintz (1974521)**
* **Baptiste Rigondaud (1973586)**
* **Loïc Poncet (1973621)**