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
- **Keycloack** verion **?**
 
Le serveur MySQL doit être en fonction et les ports **8080** à **8089** ainsi que **8180**, **8761** et **8888** doivent être libres
> Ces ports sont paramétrables dans le projet GIT [https://github.com/christophe-orsini/p12-config.git](https://github.com/christophe-orsini/p12-config.git)

### Chargement
Clonez le dépôt à cette adresse [https://github.com/christophe-orsini/Projet12.git](https://github.com/christophe-orsini/Projet12.git)

### Deploiement, Installation et Exécution
1. **Mettre le serveur MySQL en fonction**  
2. **Installer les applications**  
    - Placez vous dans le dossier où vous avez cloné le dépôt  
    - Tapez la commande `install` si vous êtes en mode console ou cliquez sur `install.bat`
3. Démarrez l'application pour exécuter les différents modules 
    - Tapez la commande `run` si vous êtes en mode console ou cliquez sur `run.bat` pour démarrer le serveur  
6. Entrer l'adresse `http://<ip_hote>:8089` (ip_hote = adresse IP ou nom de la machine dans laquelle l'application est installée) dans votre navigateur WEB préféré pour vous rendre sur le site WEB  

L'application est prète à fonctionner avec l'utilisateur :
- `pilot` mot de passe `pass` pour le rôle d'un membre connecté