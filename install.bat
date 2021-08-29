@echo off
chcp 65001
cls
echo *********************************
echo * Installation de l'application *
echo *********************************
echo *
echo *******************************************************
echo * Installation du service de configuration            *
echo *******************************************************
cd ./config-service
call mvn clean package
echo *
echo *******************************************************
echo * Installation du service d'enregistrement            *
echo *******************************************************
cd ../register-service
call mvn clean package
echo *
echo *******************************************************
echo * Installation du service de gestion des aéronefs     *
echo *******************************************************
cd ../hangar-service
call mvn clean package
echo *
echo *******************************************************
echo * Installation du service de gestion des finances     *
echo *******************************************************
cd ../financial-service
call mvn clean package
echo *
echo *******************************************************
echo * Installation du service de gestion des réservations *
echo *******************************************************
cd ../reservation-service
call mvn clean package
echo *
echo *******************************************************
echo * Installation du gateway                             *
echo *******************************************************
cd ../gateway-service
call mvn clean package
echo *
echo *******************************************************
echo * Installation du serveur d'application web           *
echo *******************************************************
cd ../airclub
call mvn clean package
cd ..
