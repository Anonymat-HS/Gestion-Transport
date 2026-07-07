# Gestion Transport

## Configuration de la base de données PostgreSQL

### 1. Installer PostgreSQL
Télécharge et installe PostgreSQL depuis [postgresql.org](https://www.postgresql.org/).  
Note le mot de passe pour l'utilisateur `postgres` pendant l'installation.

### 2. Récupérer le projet
```bash
git pull
```

### 3. Créer la base de données (une seule fois)
```bash
psql -U postgres -c "CREATE DATABASE gestion_transport;"
```

### 4. Importer les tables et les données (une seule fois)
```bash
psql -U postgres -d gestion_transport -f init.sql
```

### 5. Adapter le mot de passe (si nécessaire)
Dans `src/main/java/com/transport/n3/MenuJDBC.java`, ligne 19 :
```java
private static final String PASSWORD = "postgres";  // mettre ton mot de passe ici
```

### 6. Lancer le menu de test
```bash
mvn compile && mvn exec:java -Dexec.mainClass="com.transport.n3.MenuJDBC"
```

## Tests

```bash
mvn test
```

207 tests unitaires (JUnit 5) avec format GIVEN-WHEN-THEN.

## Contributeurs

| Matricule | Nom |
|-----------|-----|
| STD25006 | FRANÇOISE Anthonia Marie Pierre Trésore |
| STD25045 | RASOAMIARAMANANA Herimihaja Steevy |
| STD25062 | RAKOTONIAINA Misoa |
| STD25109 | RANDRIANANTENAINA Fanomezantsoa Julien Anderson |
