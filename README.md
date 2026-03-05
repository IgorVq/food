# Food API

API Spring Boot (Java 21) pour la gestion d'utilisateurs, recettes, ingredients et favoris.

## Prerequis
- Java 21
- MySQL en cours d'execution

## Configuration initiale
1. Copier le fichier de configuration exemple:

```bash
cp src/main/resources/application.properties.exemple src/main/resources/application.properties
```

2. Renseigner au minimum:
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`

Notes:
- La base `food_db` est creee automatiquement si elle n'existe pas (`createDatabaseIfNotExist=true`).
- Les tables sont generees/mises a jour automatiquement (`spring.jpa.hibernate.ddl-auto=update`).

## Lancer le projet
Depuis le dossier `food`:

```bash
./mvnw spring-boot:run
```

API disponible sur `http://localhost:8081`.

## Postman
- Importer la collection `food.postman_collection.json` dans Postman pour tester rapidement les endpoints.

## Valeurs enum
- `role` (User): `ADMIN`, `USER`
- `type` (Ingredient): `LEGUME`, `VIANDE`, `POISSON`, `FECULENT`, `PRODUIT_LAITIER`, `FRUIT`, `EPICE`, `HUILE`, `AUTRE`
- `quantityType` (RecipeIngredient): `UNITE`, `G`, `CL`

## Premiere connexion
- Endpoint login: `POST /users/login`
- Le role admin peut etre attribue directement en base de donnees si necessaire.
