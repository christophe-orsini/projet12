# Aéroclub

## Project N°12 Gestion des réservations d'un aéroclub

* Développeur : Christophe ORSINI
* Version 1.2.7

---
### Prérequis
- **Java** version **1.8.0_292**
- **Maven** version **3.8.1**
- **MySQL** version **5.7.21**
- **RabbitMQ** version **3.8.17**
- **Keycloack** verion **?**
 
Le serveur MySQL doit être en fonction et les ports **8080** à **8089** ainsi que **8761** et **8888** doivent être libres

### Chargement
Clonez le dépôt à cette adresse [https://github.com/christophe-orsini/Projet12.git](https://github.com/christophe-orsini/Projet12.git)

### Deploiement, Installation et Exécution
1. **Mettre le serveur MySQL en fonction**  
L'application est configurée pour accéder au serveur MySQL avec le login `root` et sans password  
Vous pouvez changer ces infos dans le fichier `<dossier de clonage>/apibiblio/src/main/ressources/application.properties`  
rubriques `spring.datasource.username` et `spring.datasource.password` 
2. **Installer l'application**  
    - Placez vous dans le dossier où vous avez cloné le dépôt  
    - Tapez la commande `install` si vous êtes en mode console ou cliquez sur `install.bat`
    **N'utilisez pas encore l'application**
3. Importer dans votre serveur MySQL le script `create-database-V1.0.sql` pour créer la base de données et les tables
4. Démarrez l'application pour exécuter les différents modules 
    - Tapez la commande `run` si vous êtes en mode console ou cliquez sur `run.bat` pour démarrer le serveur  
6. Entrer l'adresse `http://<ip_hote>:8080` (ip_hote = adresse IP ou nom de la machine dans laquelle l'application est installée) dans votre navigateur WEB préféré pour vous rendre sur le site WEB  

> Vous trouverez les fichiers de script *.sql dans les livrables

L'application est prète à fonctionner avec l'utilisateur :
- `adherent@ac.fr` mot de passe `adherent` pour le rôle d'utilisateur connecté