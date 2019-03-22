# JustDoIt

To-Do-Management Applikation for Java.
Schulprojekt für die Vorlesung Webbasierte Datenbankanwendungen an der DHBW Karlsruhe.


#### Kurzbeschreibung

Dies ist ein Beispiel für eine in Java realisierte, serverseitige MVC-Webanwendung.
Die Anwendung setzt dabei ganz klassisch auf der „Jakarta Enterprise Edition”
(ehemals „Java Enterprise Edition“) auf und läuft daher in einem speziell dafür
ausgelegten Applikationsserver. Sämtliche Anwendungslogik wird dabei vom Server
implementiert, so dass für jedes URL-Pattern der Anwendung ein komplett serverseitig
generierte HTML-Seite abgerufen und im Browser dargestellt wird.


## Hinweise zum Deployment

#### Verwendete Technologien

Während der Entwicklung haben wir einen speziell konfigurierten TomEE-Server
genutzt. Als Datenbank wurde die Derby-Datenbank verwendet. In unseren Dateien,
die bei der Projektabgabe hochgeladen werden, ist beides enthalten.
Die App nutzt Maven als Build-Werkzeug und zur Paketverwaltung. Auf diese Weise
werden die für Jakarta EE notwendigen APIs, darüber hinaus aber keine weiteren
Abhängigkeiten, in das Projekt eingebunden. Der Quellcode der Anwendung ist dabei
wie folgt strukturiert:

 * **Servlets** dienen als Controller-Schicht und empfangen sämtliche HTTP-Anfragen.
 * **Enterprise Java Beans** dienen als Model-Schicht und kapseln die fachliche Anwendungslogik.
 * **Persistence Entities** modellieren das Datenmodell und werden für sämtliche Datenbankzugriffe genutzt.
 * **Java Server Pages** sowie verschiedene statische Dateien bilden die View und generieren den
   auf dem Bildschirm angezeigten HTML-Code.

Folgende Entwicklungswerkzeuge kommen dabei zum Einsatz:

 * [NetBeans:](https://netbeans.apache.org/) Integrierte Entwicklungsumgebung für Java und andere Sprachen
 * [Maven:](https://maven.apache.org/) Build-Werkzeug und Verwaltung von Abhängigkeiten
 * [Git:](https://git-scm.com/") Versionsverwaltung zur gemeinsamen Arbeit am Quellcode
 * [TomEE:](https://tomee.apache.org/) Applikationsserver zum lokalen Testen der Anwendung
 * [Derby:](https://db.apache.org/derby/) In Java implementierte SQL-Datenbank zum Testen der Anwendung


#### Konfiguration der Benutzerauthentifizierung

In den Klassen justdoit.common.jpa.User sowie justdoit.common.ejb.UserBean wurde
die Benutzerverwaltung realisiert.
Die Anwendung verwaltet hierfür eine eigene Tabelle in der Datenbank, in der
die Benutzer,Passwörter und zugeordneten Benutzergruppen gespeichert werden.

Die Authentifizierung und Autorisierung werden allerdings vom
Applikationsserver vorgenommen, der hierfür entsprechend konfiguriert
werden muss. Das heißt, der Applikationsserver muss wissen, in welchen Tabellen
die Benutzer gespeichert sind. Dies erfolgt anhand den Einstellungen in der
Datei `/Web Pages/WEB-INF/web.xml`.

Dort befinden sich die anwendungsspezifischen Einstellungen, welche Aktionen
welche Berechtigungen erfordern. Am Ende wird dabei mit folgender Zeile

```
    <realm-name>justdoit</realm-name>
```

das Realm zugewiesen. Realm ist dabei nur ein schöner Begriff für eine
beliebige Datenbank mit Benutzern, ihren Passwörtern und Berechtigungen.

Im TomEE geschieht dies durch folgende Zeilen in der Konfigurationsdatei
`conf/server.xml`:
Die Definition des Realms geschieht durch folgende Zeilen in der Datei
`/Web Pages/META-INF/context.xml`:

```
    <Realm
            className      = "org.apache.catalina.realm.DataSourceRealm"

            dataSourceName = "Default-Database-Unmanaged"
            userTable      = "justdoit.justdoit_user"
            userNameCol    = "username"
            userCredCol    = "password_hash"

            userRoleTable  = "justdoit.user_group"
            roleNameCol    = "groupname"
        >
            <CredentialHandler
                className = "org.apache.catalina.realm.MessageDigestCredentialHandler"
                algorithm = "SHA-256"
            />
    </Realm>
```

Die unter `dataSourceName` genannte Datenbankverbindung muss dabei in der Datei
`conf/tomee.xml` wie folgt definiert werden:

```
    <Resource id="Derby-Sample-Managed" type="javax.sql.DataSource">
        JdbcDriver = org.apache.derby.jdbc.ClientDriver
        JdbcUrl    = jdbc:derby://localhost:1527/sample
        UserName   = app
        Password   = app
        JtaManaged = true
    </Resource>

    <Resource id="Derby-Sample-Unmanaged" type="javax.sql.DataSource">
        JdbcDriver = org.apache.derby.jdbc.ClientDriver
        JdbcUrl    = jdbc:derby://localhost:1527/sample
        UserName   = app
        Password   = app
        JtaManaged = false
    </Resource>
```

Das Beispiel geht dabei von der Derby-Beispieldatenbank aus, die Bestandteil
von Netbeans ist.


## Hinweise zum E-Mail-Versand

Der E-Mail-Versand wird mit der [JavaMail API](https://javaee.github.io/javamail/docs/api/) umgesetzt.
Ein Teil der benötigten Einstellungen wird in einer JSON Datei gespeichert:
`src/main/resources/mailConfig.json`

```
{
    "from": "",
    "host": "",
    "port": "",
    "username": "",
    "password": ""
}
```

* `from`: Adresse die als Absenderadresse angezeigt werden soll.
* `host`: Adresse des Postausgangssevers (SMTP-Server)
* `port`: Port des Postausgangsservers (SMTP-Server)
* `username`: Benutzername des Kontos beim Mail-Provider
* `password`: Passwort des Kontos beim Mail-Provider
