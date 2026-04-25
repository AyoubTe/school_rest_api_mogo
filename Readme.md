# 🎓 School Management API (Spring Boot + MongoDB)

Une API RESTful développée avec **Spring Boot** et **MongoDB** pour la gestion complète d'un établissement scolaire. Le système gère les utilisateurs (Administrateurs, Professeurs, Étudiants), les cours, les notes et le suivi des devoirs.

---

## 🛠️ Stack Technique

* **Langage :** Java 21
* **Framework :** Spring Boot
* **Base de données :** MongoDB
* **ORM / ODM :** Spring Data MongoDB
* **Sécurité :** Spring Security + JWT (JSON Web Tokens)
* **Outils complémentaires :** Lombok, MapStruct, Swagger/OpenAPI (Springdoc)
* **Build Tool :** Gradle

---

## 🏗️ Architecture des Données (MongoDB)

Le projet utilise une approche relationnelle adaptée à MongoDB via l'utilisation des annotations `@DBRef`.

Les collections principales sont les suivantes :
* **`schools`** : Les établissements scolaires.
* **`admins`**, **`teachers`**, **`students`** : Les utilisateurs, héritant d'une classe commune `User`.
* **`courses`** : Les cours dispensés (liés à un professeur et à des étudiants).
* **`assignments`** & **`assignment_details`** : Gestion et suivi des devoirs à rendre.
* **`student_course_details`** & **`grade_details`** : Inscriptions aux cours et bulletins de notes.

> **💡 Note technique sur les identifiants :** > Tous les IDs des entités sont typés en `String` côté Java afin de correspondre nativement au format `ObjectId()` de MongoDB.

---

## 🚀 Installation & Démarrage

### 1. Prérequis
* Java 21 installé.
* MongoDB (Serveur local ou cluster Atlas) en cours d'exécution sur le port `27017`.
* Gradle installé (ou utilisation du wrapper `./gradlew`).

### 2. Initialisation de la base de données
Le projet inclut un script d'initialisation complet pour peupler la base de données avec des données de test cohérentes (mock data) et des relations générées dynamiquement.

1. Ouvrez votre terminal MongoDB (mongosh) ou un outil comme MongoDB Compass.
2. Exécutez le script d'initialisation fourni :
   ```bash
   mongosh localhost:27017/school_db init.js