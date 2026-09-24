# Face Detection App

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![OpenCV](https://img.shields.io/badge/OpenCV-JavaCV%201.5.10-blue.svg)](https://bytedeco.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Une application web moderne de vision par ordinateur développée avec **Spring Boot 3** et **JavaCV / OpenCV**. Elle permet d'analyser des images importées pour y détecter automatiquement des visages à l'aide des classificateurs en cascade (*Haar Cascade*) et d'annoter les résultats en temps réel.

---

## Fonctionnalités

-  **Traitement d'Image avec OpenCV** : Analyse automatique de la structure faciale et dessin dynamique des rectangles de détection.
-  **API REST Asynchrone** : Envoi multipart et traitement via un contrôleur Spring Boot réactif.
-  **Interface Dashboard SaaS Modernisée** :
  - Support du **Drag & Drop** (glisser-déposer) et prévisualisation avant analyse.
  - Statistiques en temps réel (nombre de visages détectés, statut du modèle).
  - Design réactif (Responsive) avec retour visuel (loadings, spinners).
-  **Architecture Clean & Découplée** : Séparation stricte DTO / Service / Controller.

---

## Technologies Utilisées

- **Backend** : Java 25, Spring Boot 3, Spring MVC, Maven
- **Vision par Ordinateur** : JavaCV (Bindings Java d'OpenCV), Haar Cascade Classifier (`haarcascade_frontalface_default.xml`)
- **Frontend** : HTML5, CSS3 (Variables & Grid/Flexbox), JavaScript ES6 Vanilla (Fetch API)

---

##  Architecture du Projet

``text
facedetection/
├── src/
│   ├── main/
│   │   ├── java/com/romeo/facedetection/
│   │   │   ├── controller/      # Contrôleurs REST (Endpoints API)
│   │   │   ├── dto/             # Objets de transfert de données (FaceDetectionResponseDTO)
│   │   │   └── service/         # Logique métier OpenCV & encodage Base64
│   │   └── resources/
│   │       ├── cascades/        # Classificateur Haar Cascade XML
│   │       └── static/          # Frontend Web (HTML, CSS, JS)
└── pom.xml                      # Configuration des dépendances Maven

