@echo off
chcp 65001
cls
if not exist .\config-service\target\config-service-1.0.0.jar (
	echo **************************************************************************************
	echo * Le microservice n'est pas installée !                                              *
	echo * Exécuter la commande 'install' en mode console ou double-cliquer sur 'install.bat' *
	echo * pour installer les microservices                                                   *
	echo **************************************************************************************
	pause
	goto:eof
) else (
	cls
	echo ********** Démarrage du microservice de configuration
	start "config-service" /MIN java -jar .\config-service\target\config-service-1.0.0.jar
	echo Attente du démarrage du microservice
	timeout /t 15 /NOBREAK 
)
if not exist .\register-service\target\register-service-1.0.0.jar (
	echo **************************************************************************************
	echo * Le microservice n'est pas installée !                                              *
	echo * Exécuter la commande 'install' en mode console ou double-cliquer sur 'install.bat' *
	echo * pour installer les microservices                                                   *
	echo **************************************************************************************
	pause
	goto:eof
) else (
	cls
	echo ********** Démarrage du microservice d'enregistrement
	start "register-service" /MIN java -jar .\register-service\target\register-service-1.0.0.jar
	echo Attente du démarrage du microservice
	timeout /t 20 /NOBREAK 
)
if not exist .\hangar-service\target\hangar-service-1.0.0.jar (
	echo **************************************************************************************
	echo * Le microservice n'est pas installée !                                              *
	echo * Exécuter la commande 'install' en mode console ou double-cliquer sur 'install.bat' *
	echo * pour installer les microservices                                                   *
	echo **************************************************************************************
	pause
	goto:eof
) else (
	cls
	echo ********** Démarrage du microservice de gestion des aéronefs
	start "hangar-service" /MIN java -jar .\hangar-service\target\hangar-service-1.0.0.jar
	echo Attente du démarrage du microservice
	timeout /t 15 /NOBREAK 
)
if not exist .\financial-service\target\financial-service-1.0.0.jar (
	echo **************************************************************************************
	echo * Le microservice n'est pas installée !                                              *
	echo * Exécuter la commande 'install' en mode console ou double-cliquer sur 'install.bat' *
	echo * pour installer les microservices                                                   *
	echo **************************************************************************************
	pause
	goto:eof
) else (
	cls
	echo ********** Démarrage du microservice de gestion des finances
	start "financial-service" /MIN java -jar .\financial-service\target\financial-service-1.0.0.jar
	echo Attente du démarrage du microservice
	timeout /t 15 /NOBREAK 
)
if not exist .\reservation-service\target\reservation-service-1.0.0.jar (
	echo **************************************************************************************
	echo * Le microservice n'est pas installée !                                              *
	echo * Exécuter la commande 'install' en mode console ou double-cliquer sur 'install.bat' *
	echo * pour installer les microservices                                                   *
	echo **************************************************************************************
	pause
	goto:eof
) else (
	cls
	echo ********** Démarrage du microservice de gestion des réservations
	start "reservation-service" /MIN java -jar .\reservation-service\target\reservation-service-1.0.0.jar
	echo Attente du démarrage du microservice
	timeout /t 15 /NOBREAK 
)
if not exist .\gateway-service\target\gateway-service-1.0.0.jar (
	echo **************************************************************************************
	echo * Le gateway n'est pas installée !                                                   *
	echo * Exécuter la commande 'install' en mode console ou double-cliquer sur 'install.bat' *
	echo * pour installer les microservices et le gateway                                     *
	echo **************************************************************************************
	pause
	goto:eof
) else (
	cls
	echo ********** Démarrage du gateway
	start "gateway-service" /MIN java -jar .\gateway-service\target\gateway-service-1.0.0.jar
	echo Attente du démarrage du gateway
	timeout /t 15 /NOBREAK 
)
if not exist .\airclub\target\airclub-webapp-1.0.0.jar (
	echo **************************************************************************************
	echo * Le serveur d'application web n'est pas installé                                    *
	echo * Exécuter la commande 'install' en mode console ou double-cliquer sur 'install.bat' *
	echo * pour installer les microservices, le gateway et le serveur d'application           *
	echo **************************************************************************************
	goto:eof
	exit
) else (
	cls
	echo ********** Démarrage du serveur d'application web
	start "airclub" /MIN java -jar .\airclub\target\airclub-webapp-1.0.0.jar
	echo Attente du démarrage du serveur d'application
	timeout /t 20 /NOBREAK 
)
cls
echo *********** L'application est prête à être utilisée