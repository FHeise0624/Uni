# Übung 2

## Klassendiagramm:
Das Klassendiagramm wurde mittels der Software UMLet erstellt.
Um Sicherzustellen, dass dieses eingesehen werden kann, liegt es im zip-Archiv 
sowohl als .jpeg-File als auch als .uxf-File ab.

Im Klassendiagramm sind alle vorgegebenen Interfaces in Grün, als auch die daraus abgeleiteten Klassen abgebildet.
Diese sind in ihren jeweiligen Packages, entsprechend der Datenstruktur des Programmcodes
dargestellt.
Methoden, welche auf basis der Vererbung vorhanden sind, wurden in den erbenden Klassen nicht explizit
aufgeführt, auch wenn diese in der Implementierung vorhanden sind.

## Implementierung:
Die Implementierung ist, obwohl nicht vollständig, nach den Vorgaben aus dem Use-Case-Diagramm,
sowie den vordefinierten Interfaces erfolgt.
Zum jetzigen Zeitpunkt ist es nicht möglich über die Klassen hinweg, mittels der definierten
Abhängigkeiten Informationsabfragen durchzuführen.

In der Klasse AbstimmungsReader, lassen sich durch Hinterlegen des Dateipfades zu dem Abstimmungs-Archiv,
die .xls und .xlsx Dateien auslesen. Die ausgelesenen Daten werden jedoch noch nicht in entsprechenden Datenstrukturen
gespeichert, dem entsprechen ist ein Zugriff von außen nicht möglich.
 * Der Dateipfad muss im Code in Zeile 24 in der Variable "folderpath" hinterlegt werden.

In der Klasse RedenReader, lassen sich bis Dato nur die Informationen zu dem ersten Redner, welcher in einer
der Dateien als erstes genannt wird ausgeben, diese werden jedoch ebenfalls noch nicht in entsprechenden
Datenstrukturen gespeichert.
* Der Dateipfad muss im Code in Zeile 25 in der Variable "filepath" hinterlegt werden.

In der Klasse StammdatenReader, werden sämtliche Informationen zuden im XML-File hinterlegten Abgeordneten
ausgelesen und in verschachtelten Hashmaps gespeichert. Diese sind jedoch noch nicht von außerhalb der Klasse
zugänglich.
* Der Dateipfad muss im Code in Zeile 35 in der Variable "filename" hinterlegt werden.

In allen drei Fällen, lässt sich die Funktionalität testen, indem man die jeweilige Methode innerhalb der Klasse ausführt.
In allen drei Klassen ist zu diesem Zweck zurzeit noch eine main Methode enthalten um die Ausführbarkeit zu gewährleisten.
