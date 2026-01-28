# 🐱‍👤 Pokémon Backend API

Dieses Projekt ist ein **RESTful Backend** zur Verwaltung von Pokémon-Daten.  
Es wurde mit **Jakarta EE**, **Payara Server**, **JPA (Jakarta Persistence)** und **MySQL** entwickelt.

Der Backend-Service stellt CRUD-Endpunkte (Create, Read, Update, Delete) für Pokémon bereit und ist so konzipiert, dass er später um **Benutzerverwaltung und Authentifizierung** erweitert werden kann.

---

## 🚀 Technologien

- **Java 17**
- **Jakarta EE**
    - JAX-RS (REST)
    - CDI
    - JPA
- **Payara Server**
- **MySQL**
- **Maven**
- **Postman** (für API-Tests)
- **DBeaver** (für Datenbankverwaltung)

---

## 📦 Features (aktueller Stand)

### Pokémon
- Alle Pokémon abrufen
- Ein einzelnes Pokémon abrufen
- Neues Pokémon anlegen
- Pokémon aktualisieren
- Pokémon löschen
- Automatisch generierte IDs (MySQL `AUTO_INCREMENT`)

### Geplant
- Benutzer (`User`) Entity
- Beziehung **User ↔ Pokémon (1:n)**
- Authentifizierung (Login)
- Passwort-Hashing
- Benutzerprofil (Name ändern, Passwort ändern, Profil löschen)
- Anzeige der Pokémon eines bestimmten Benutzers

---

## 🗂️ Projektstruktur (vereinfacht)
src/main/java

└── com.pokemon.pokemon_backend  
├── model # JPA Entities (Pokemon, später User)  
├── service # Business-Logik (JPA, Transactions)  
└── resource # REST Resources (JAX-RS)

---

## 🗄️ Datenbank

- **MySQL**
- Verbindung über **JNDI Datasource** in Payara
- Tabellen werden automatisch durch JPA erzeugt

### Verwendete Tabelle
- `pokemon`

---

## ⚙️ Konfiguration

### `persistence.xml`
Pfad:
src/main/resources/META-INF/persistence.xml

Verwendet:
- JTA
- JNDI Datasource (`jdbc/pokemonDS`)
- Automatische Schema-Erstellung

---

## 🔌 REST-Endpunkte

### Pokémon

| Methode | Endpoint | Beschreibung |
|------|--------|-------------|
| GET | `/pokemons` | Alle Pokémon abrufen |
| GET | `/pokemons/{id}` | Pokémon nach ID |
| POST | `/pokemons` | Neues Pokémon erstellen |
| PUT | `/pokemons/{id}` | Pokémon aktualisieren |
| DELETE | `/pokemons/{id}` | Pokémon löschen |

### Beispiel: POST `/pokemons`

```json
{
  "name": "Pikachu",
  "hp": 35,
  "cp": 55
}
```
```
Antwort:

201 Created

Pokémon mit generierter ID
```

---

## 🧪 Tests

- REST-Tests mit Postman

- Datenbankprüfung mit DBeaver

- DELETE-Anfragen ohne Request Body

---

## 🔐 Sicherheit (Hinweis)

⚠️ Aktuell gibt es noch keine Authentifizierung.
Passwörter werden noch nicht gespeichert.

Geplante Umsetzung:

- Benutzer-Entity

- Passwort-Hashing (BCrypt)

- JWT-basierte Authentifizierung

---

## Autor
- Entwickelt von [Raoul Tchangou]
- Projekt für Lernzwecke und Portfolio
- Projektstatus: In Entwicklung
