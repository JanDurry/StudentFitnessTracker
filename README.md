# StudentFitnessTracker

Contributors: Lyonel Behringer und Jan Durry

## Projektbeschreibung

Die App dient der Organisierung von und der Motivation für sportliche Aktivitäten.
Zurückgelegte Distanzen werden mithilfe von Koordinaten gemessen und je nach Aktivität wird
der Kalorienverbrauch berechnet. Der Verlauf kann gespeichert werden. (Je nach verfügbarer Zeit: 
Für sportliche Aktivität werden Punkte vergeben. Bei längerer Inaktivität erscheinen Pushnachrichten 
zur Motivation.)

## Kernfeatures
- Distanzmessung für Laufen/Radfahren
- Standortbestimmung
- Speicherung von Werten in Datenbank
- Kalorienrechner
- Notifications

## Feature

Distanzmessung: GPS-Abfragen, nach Beendigung der Aktivität Berechnung der zurückgelegten Distanz
Standortbestimmung: GPS-Abfrage
Kalorienrechner: Berechnung anhand von zurückgelegter Distanz (Laufen/Radfahren)
Speicherung von Werten: Öffnen einer Datenbankverbindung, Ausführen von Anfragen, Schließen der
Verbindung
Notification: Push-Nachricht, wenn Kalorienverbrauch erreicht wurde oder nicht

### Zusatzfeature

Punkteverteilung: Speicherung in einer Liste, bei Erreichen eines Etappenziels Belohnung (z.B.
Bild/motivierende Nachricht)
