<!--
Template für die Profil-Seite.
@author Felix
-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
        <!-- Link zum Stylesheet für die Profilseite -->
        <link rel="stylesheet" href="/profile.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <!-- JQuery einbinden -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <!-- Definition Seiteninhalt -->
    <body>

    <!-- Definition Dropdown-Menü -->
        <div class="dropdown">

            <!-- Dropdown-Menü-Icon -->
            <button class="dropbtn"><i class="fas fa-bars"></i></button>

            <!-- Inhalt des Dropwdown-Menüs in Abhängigkeit von Nutzer-Rechten-->
            <div class="dropdown-content">

                <!-- Inhalt ohne Login verfügbar -->
                <a href="/home"><button>Homepage</button></a>

                <!-- Inhalt für eingeloggte Nutzer der Gruppe Admin verfügbar -->
                <#if isAuthenticatedAdmin>
                    <a href="/userManagement"><button>Nutzer Management</button></a>
                </#if>

                <!--Inhalt für alle eingeloggten Nutzer verfügbar -->
                <#if isAuthenticatedUser>
                    <button class="logout-button">Logout</button>
                </#if>
            </div>
        </div>

        <!-- Container für Profilinformationen -->
        <div class="container">
            <h1>Profil</h1>

            <!-- Anzeige der Benutzerinformationen die mit dem Model übergeben werden -->
            <p>Vorname: ${vorname}<br>
            <p>Nachname: ${nachname}<br>
            <p>Benutzername: ${username} <br>
            <p>E-Mail: ${email}<br>
            <p>Rolle: ${rolle}<br>
            <p>ID: ${id}<br>

                <!-- Button zum Aufrufen des Änderungsformulars für das Passwort -->
            <button class="pw">Passwort ändern</button>
        </div>

        <!-- Popup zur Anzeige des Änderungsformulars für das Passwort -->
        <div class="popup-content" id="popup">
            <!-- Festlegen, dass das Popup initial nicht sichtbar ist -->
            <span class="close" id="close">&times;</span>

            <!-- Inhalt des Popups mit Eingabefeldern -->
            <h2>Passwort ändern</h2>
            <input type="password" id="altesPasswort" placeholder="Altes Passwort"><br>
            <input type="password" id="neuesPasswort" placeholder="Neues Passwort"><br>
            <button id="submitPassword">Ändern</button>
        </div>

        <!-- Versteckte inputs zur speicherung aller anderen Nutzerdaten-->
        <input type="hidden" id="nutzerId" value="${id}">
        <input type="Hidden" id="benutzername" value="${username}">
        <input type="Hidden" id="email" value="${email}">
        <input type="Hidden" id="vorname" value="${vorname}">
        <input type="Hidden" id="nachname" value="${nachname}">
        <input type="Hidden" id="rolle" value="${rolle}">

        <!-- Implementation der Funktionalitäten -->
        <script>

             // Funktion des Logout-Button
            $(document).ready(function() {

                // Klick-Event des Buttons
                $('.logout-button').on('click', function() {

                    // Logout-Anfrage an den Server senden
                    $.post("/logout", function(response) {

                        // Weiterleitung auf die Homepage nach erfolgreichem Logout
                        window.location.href=("/home");
                    });
                });
            });

            // Funktion, des Passwort ändern Buttons.
            $(document).ready(function() {

                // Klick-Event, des Passwort ändern Buttons
               $('.pw').on('click', function () {
                 $('#popup').css('display', 'block'); // Popup anzeigen
               });

               // Klick-Event, des schließen Button
               $('#close').on('click', function() {
                  $('#popup').css('display', 'none'); //Popup schließen
               });

               // Klick-Event, des Ändern Button
               $('#submitPassword').on('click', function () {

                   // Abspeichern der Benutzerinformationen aus den hidden Inputs und dem Passwort Formular
                  var nutzerId = $('#nutzerId').val();
                  var benutzername = $('#benutzername').val();
                  var email = $('#email').val();
                  var rollealt = $('#rolle').val();
                  var rolleUpdate = [rollealt.slice(1, -1)]; // Enfternen der Klammern um korrektes Format an Backend zu übergeben
                  var nachname = $('#nachname').val();
                  var vorname = $('#vorname').val();
                  var altesPasswort = $('#altesPasswort').val();
                  var neuesPasswort = $('#neuesPasswort').val();

                  // Daten im korrekten JSON-String ablegen
                  var requestData = {
                    benutzerId: nutzerId,
                    benutzername: benutzername,
                    email: email,
                    gruppenIds: rolleUpdate,
                    nachname: nachname,
                    vorname: vorname,
                    password: neuesPasswort
                  };

                  // Änderung des Passworts in der Datenbank via put Methode der API.
                  $.ajax({
                     url: "/benutzer",
                     method: "PUT",
                     contentType: "application/json",
                     data: JSON.stringify(requestData),
                     success: function(response){
                         alert(response);
                         $('#popup').css('display', 'none'); // Popup schließen bei erfolgter Änderung.
                     } ,
                      error: function(xhr, status, error) {
                         // Ausgabe einer Fehlermeldung, wenn die Änderung nicht möglich ist.
                         alert("Fehler beim Aktualisieren des Passworts: " + xhr.responseText);
                      }
                  });
               });
            });
        </script>
    </body>
</html>
