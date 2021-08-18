# Aéroclub

## Project N°12 Gestion des réservations d'un aéroclub

* Développeur : Christophe ORSINI
* Version 1.0.0

---
### Prérequis
- **Java** version **1.8.0_292**
- **Maven** version **3.8.1**
- **MySQL** version **5.7.21**
- **RabbitMQ** version **3.8.17**
- **Keycloack** verion **14.0.0**
 
>Le serveur MySQL doit être en fonction et accessible par le port 3306  
>Le serveur RabbitMQ doit être en fonction est accessible par le port 5672  
>Le serveur Keycloak (standalone) doit être en fonction est accessible par le port 8180  
>Les ports **8080** à **8089** ainsi que **8180**, **5672**, **8761** et **8888** doivent être libres

### Chargement
Clonez le dépôt à cette adresse [https://github.com/christophe-orsini/Projet12.git](https://github.com/christophe-orsini/Projet12.git)

### Configuration
Pour la configuration, les microservices et le gateway utilisent **Spring Cloud Config Server**  
Les fichiers de configuration sont dans le dépôt GIT suivant : [https://github.com/christophe-orsini/p12-config.git](https://github.com/christophe-orsini/p12-config.git)

Le fichier de configuration (`application.properties`) de **Spring Cloud Config Server** se trouve dans le répertoire :  
`<dossier destination du clonage>/config-service/src/main/ressources`

Le fichier de configuration (`application.properties`) de l'application principale se trouve dans le répertoire :  
`<dossier destination du clonage>/airclub/src/main/ressources`

### Deploiement / Installation
1. **Mettre le serveur MySQL en fonction**  
2. **Installer les applications**  
    - Placez vous dans le dossier où vous avez cloné le dépôt  
    - Tapez la commande `install` si vous êtes en mode console ou cliquez sur `install.bat`
      
>Cette commande va installer un par un les microservices ainsi que le gateway et le serveur d'application

### Exécution
1. Assurez-vous que les serveurs MySQL, RabbitMq et Keycloak sont en fonction 
    - Si le serveur Keycloak n'est pas en fonction, exécutez le script `RunKeycloak.bat`
2. Démarrez l'application pour exécuter les différents modules 
    - Tapez la commande `run` si vous êtes en mode console ou cliquez sur `run.bat` pour démarrer le serveur  
3. Entrer l'adresse `http://<ip_hote>:8089` (ip_hote = adresse IP ou nom de la machine dans laquelle l'application est installée) dans votre navigateur WEB préféré pour vous rendre sur le site WEB  

L'application est prète à fonctionner avec l'utilisateur :
- `pilot` mot de passe `pass` pour le rôle d'un membre connecté