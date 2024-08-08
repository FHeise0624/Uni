# Übung 3

## Package: Database

### MongoDBConnectionHandler
Der Zugriff auf die Datenbank wird über den ConnectionHandler abgewickelt.
Damit man den Zugriff auf die Datenbank erhält, muss man in der jeweiligen Database-Datei, den Pfad zu seiner Zugansdaten-Datei mit .txt Endung in der 
Path-Variable speicher. (Zu kontrollzwecken liegt meine Datei mit den Zugangsdaten im Resoucenordner ab.)
Der Zugriff auf die Datenbank erfolg dann automatisch.

### DatabaseImpl
Mittels der DatabaseImpl-Klasse werden sämtliche Daten in die Datenbank eingepflegt. Die Quelldateien liegen 
ebenfalls im Recourcen-Ordner ab. Ebenfalls werden die Collections in der BD in dieser Klasse erstellt.

## Package: helper

### DatabaseCredentialReader
Dies ist eine Hilfsklasse zum Einlesen der Anmeldedaten für die MongoDB.

### DateHelper
Diese Klasse dient zum Formatieren von Daten vom Typ Date in ein einheitliches Format.

### MPImporter
Diese Klasse liest die MdB-Stammdaten aus der XML-Datei aus, erstell Abgeordneten Objekte und gibt diese in einem
Set zurück.

### SpeechImporter
Diese Klasse entpackt das Bundestagsreden20.zip Archiv, liest die darin enthaltenen XML-Dateien aus und erstellt für 
jede Rede ein Speech-Objekt, welches in einem Set zurückgegeben wird.

### XMLReader
Der XMLReader dient dazu, XML-Dateien zu Parsen damit die darin enthaltenen Informationen weiterverarbeitet 
werden können.

## Package: data

### Agenda
Aktuell noch ein Interface ohne Funktion.

### Meeting
Aktuell noch ein Interface ohne Funktion.

### MP
Das Interface bietet die Schnittstelle und Rahmenbedingungen für die MP-Klasse, welche zur darstellung der Daten von 
Abgeordneten dient.

### Speech
Das Interface bietet die Schnittstelle und Rahmenbedingungen für die Speech-Klasse, welche zu darstellung der Daten 
von Reden dient.

## Package data.impl

### MPImpl
Diese Klasse implementiert das Interface MP und bietet die möglichkeit auf die Daten von Abgeordneten zuzugreifen 
und diese mittels der setter-Methoden zu überschreiben.

### SpeechImpl
Diese Klasse implementiert das Interface Speech und bietet die Möglichkeit auf die Daten von Reden zuzugreifen und 
diese mittels der setter-Methoden zu überschreiben.