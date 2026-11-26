<div align="center">

# 🤖 Catalogue AI Agent

**Plateforme intelligente de gestion de catalogue produits**
*Spring Boot · Angular · MCP · Agent IA*

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-17-red?style=flat-square&logo=angular)](https://angular.io/)
[![Maven](https://img.shields.io/badge/Maven-3.9-yellow?style=flat-square&logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)

[![GitHub stars](https://img.shields.io/github/stars/EyalbenRadhouane/catalogue-ai-agent?style=social)](https://github.com/EyalbenRadhouane/catalogue-ai-agent)
[![GitHub forks](https://img.shields.io/github/forks/EyalbenRadhouane/catalogue-ai-agent?style=social)](https://github.com/EyalbenRadhouane/catalogue-ai-agent/fork)

</div>

---

## Description

**Catalogue AI Agent** est une application full-stack modulaire qui combine un backend **Spring Boot**, un frontend **Angular** et un serveur **MCP (Model Context Protocol)** permettant à un agent IA de manipuler dynamiquement le catalogue de produits via un chat intelligent.

L'agent IA est capable de :
- 🔍 Rechercher des produits par nom, catégorie ou prix
- ➕ Ajouter / modifier / supprimer des produits
- 📊 Analyser les stocks et les tendances de vente
- 💬 Répondre en langage naturel aux demandes utilisateurs

---

## Fonctionnalités

| Module | Description |
|--------|-------------|
| **gestion-catalogue** | API REST Spring Boot pour la gestion CRUD des produits |
| **chat-front** | Interface Angular avec chatbot IA intégré |
| **mcp-server** | Serveur MCP exposant les outils du catalogue à l'agent |
| **Agent IA** | Prompt système enrichi (rôle, règles, outils) |

---

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     UTILISATEUR (Browser)                   │
└──────────────────────────┬──────────────────────────────────┘
                           │ HTTP / WebSocket
                           ▼
              ┌────────────────────────┐
              │   💻 chat-front        │
              │      (Angular)         │
              └───────────┬────────────┘
                          │ REST API
                          ▼
              ┌────────────────────────┐         ┌──────────────────┐
              │ 🧩 gestion-catalogue   │◄────────┤ 🤖 mcp-server    │
              │   (Spring Boot API)    │  Tools  │  (MCP Protocol)  │
              └───────────┬────────────┘         └────────┬─────────┘
                          │                               │
                          ▼                               ▼
                   ┌─────────────┐                ┌─────────────┐
                   │  Database   │                │  LLM Model  │
                   └─────────────┘                └─────────────┘
```

---

## Stack Technique

### Backend
- **Java 17+**
- **Spring Boot 3.x** (Web, Data JPA, Validation)
- **MySQL (via XAMPP)** (base de données de développement)
- **H2** (base de données en mémoire, pour les tests)
- **Maven** (gestion des dépendances)

### Frontend
- **Angular 17+**
- **TypeScript**
- **HTML5 / CSS3 / Bootstrap**

### IA & MCP
- **Agent IA** avec prompt système enrichi
- **MCP Server** (Model Context Protocol)
- **Outils exposés** : `list_products`, `add_product`, `update_product`, `delete_product`, `search_products`

---

## Structure du Projet

```
catalogue-ai-agent/
├── gestion-catalogue/          # Module Spring Boot (API REST)
│   ├── src/main/java/
│   ├── src/main/resources/
│   └── pom.xml
├── chat-front/                 # Module Angular (Frontend)
│   ├── src/app/
│   ├── src/assets/
│   └── package.json
├── mcp-server/                 # Module MCP (Serveur d'outils IA)
│   ├── src/
│   └── pom.xml
├── .gitignore
├── README.md
└── pom.xml                     # POM parent (multi-modules)
```

---

## Installation

### Prérequis

| Outil | Version minimale | Lien |
|-------|------------------|------|
| JDK | 17 | [Adoptium](https://adoptium.net/) |
| Maven | 3.9 | [maven.apache.org](https://maven.apache.org/) |
| Node.js | 20 LTS | [nodejs.org](https://nodejs.org/) |
| Angular CLI | 17 | `npm install -g @angular/cli` |
| Git | 2.40 | [git-scm.com](https://git-scm.com/) |

### Cloner le dépôt

```bash
git clone https://github.com/EyalbenRadhouane/catalogue-ai-agent.git
cd catalogue-ai-agent
```

### Backend — Spring Boot

```bash
cd gestion-catalogue
mvn clean install
mvn spring-boot:run
# ✅ API disponible sur http://localhost:8080
```

### Frontend — Angular

```bash
cd chat-front
npm install
ng serve
# ✅ Interface disponible sur http://localhost:4200
```

### MCP Server

```bash
cd mcp-server
mvn clean install
mvn spring-boot:run
# ✅ Serveur MCP actif sur http://localhost:8081
```

---

## Endpoints API (Exemples)

| Méthode | URL | Description |
|---------|-----|-------------|
| `GET` | `/api/products` | Lister tous les produits |
| `GET` | `/api/products/{id}` | Détail d'un produit |
| `POST` | `/api/products` | Créer un produit |
| `PUT` | `/api/products/{id}` | Modifier un produit |
| `DELETE` | `/api/products/{id}` | Supprimer un produit |
| `POST` | `/api/chat` | Envoyer un message à l'agent IA |

**Exemple de requête :**

```bash
curl -X GET http://localhost:8080/api/products
```

**Réponse :**

```json
[
  {
    "id": 1,
    "nom": "Ordinateur Portable",
    "prix": 899.99,
    "categorie": "Électronique",
    "stock": 15
  }
]
```

---

## Configuration

Modifier `gestion-catalogue/src/main/resources/application.yml` :

---
yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/catalogue_db
    username: root
    password:
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect

ai:
  model: gemini
  api-key: ${GEMINI_API_KEY}
---

## Roadmap

- CRUD produits (Spring Boot)
- Interface chat (Angular)
- Serveur MCP
- Enrichissement du prompt système (rôle, règles, outils)
- Authentification JWT
- Dashboard analytique
- Déploiement Docker
- Tests unitaires & intégration

---

## Contribuer

Les contributions sont les bienvenues !

```bash
git checkout -b feature/ma-nouvelle-fonctionnalite
git commit -m "feat: description de la fonctionnalité"
git push origin feature/ma-nouvelle-fonctionnalite
```

Merci de suivre les conventions [Conventional Commits](https://www.conventionalcommits.org/) :
- `feat:` nouvelle fonctionnalité
- `fix:` correction de bug
- `docs:` documentation
- `refactor:` refactorisation
- `test:` ajout de tests

---
## Auteur

**Eyal Ben Radhouane**
GitHub : [@EyalbenRadhouane](https://github.com/EyalbenRadhouane)
---
