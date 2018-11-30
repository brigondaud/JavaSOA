JavaSOA

A faire manuellement pour spark:
conf/slaves (ajouter toutes les ip du slaves sur ce fichier dans le master)
mettre dans le fichier conf/spark-env.sh: ajouter SPARK_MASTER_HOST=<@IP-Master>

#Installation de Spark

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
