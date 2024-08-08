<!--
Template für die Reden Seite
@author Felix
 -->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reden</title>

        <!-- Stylesheets einbinden -->
        <link rel="stylesheet" href="/speeches.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- JQuery einbinden -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <!-- Inhalt der Seite definieren -->
    <body>
        <div class="container">
            <h1>Reden</h1>

            <!-- Dropdown Menü -->
            <div class="dropdown">

                <!-- Dropdown-Menü-Button -->
                <button class="dropbtn"><i class="fas fa-bars"></i></button>

                <!-- Inhalt des Dropdown -->
                <div class="dropdown-content">

                    <!-- Inhalt ohne Login verfügbar -->
                    <a href="/home"><button>Homepage</button></a>

                    <!-- Inhalt nur ohne Login verfügbar -->
                    <#if !isAuthenticated>
                        <a href="/login"><button>Login</button></a>
                    </#if>

                    <!-- Inhalt ohne Login verfügbar -->
                    <a href="/plenaryProtocols"><button>Plenar Protokolle</button></a>

                    <!-- Inhalt für eingeloggte Nutzer verfügbar -->
                    <#if isAuthenticatedUser>
                        <button class="logout-button">Logout</button>
                        <a href="/profile"><button>Profil</button></a>
                    </#if>

                    <!-- Inhalt für eingeloggte Manager verfügbar -->
                    <#if isAuthenticatedManager>
                        <a href="/abgeordnetenManagement"><button>Abgeordneten Management</button></a>
                    </#if>

                    <!-- Inhalt für eingeloggte Admin verfügbar -->
                    <#if isAuthenticatedAdmin>
                        <a href="/userManagement"><button>Nutzer Management</button></a>
                    </#if>
                </div>
            </div>

            <!-- Sitzungsmanagement Optionen für eingeloggte Nutzer -->
            <#if isAuthenticatedUser>
                <button id="sitzung">Parlamentssitzung bearbeiten</button>
                <button id="edit" style="display: none">Redner bearbeiten</button>
                <button id="kommentar">Kommentarinfo ergänzen</button>
            </#if>

            <!-- Redenmanagement Optionen -->
            <button id="hinzu" style="display: none">Redner hinzufügen</button>
            <button id="delete" style="display: none">Redner entfernen</button>
            <button id="get">Rede runterladen</button>

            <!-- Sitzungsmanagement Optionen für eingeloggte Manager -->
            <#if isAuthenticatedManager>
                <button id="sitzungEntf" style="display: none">Sitzung löschen</button>
                <button id="addSitzung" style="display: none">Sitzung erstellen</button>
            </#if>

            <!-- Buttons zur bearbeitung von Rednern und Fraktionen in Kommentaren -->
            <button id="rednerZuKommentar" style="display: none">Redner zuweisen</button>
            <button id="rednerVonKommentar" style="display: none">Redner entfernen</button>
            <button id="fraktionZuKommentar" style="display: none">Fraktion zuweisen</button>
            <button id="fraktionVonKommentar" style="display: none">Fraktion entfernen</button>

            <!-- Suchformular für eine bestimme Rede -->
            <form id="rede">
                <h1>Eine bestimme Rede suchen</h1>

                <!-- Auswahl anhand der redenId -->
                <label for="redenId">Reden-ID:</label>
                <input type="text" id="redenId" name="redenId">

                <!-- Auswahl für das Datum der Rede -->
                <label for="datum">Datum:</label>
                <input type="date" id="datum" name="datum">

                <!-- Button zum Abrufen der Rede -->
                <button id="abruf">Rede abrufen</button>
            </form>

            <!-- Popup zum Darstellen der gesuchten Rede -->
            <popup id="geladeneRede" style="display: none">
                <p id="redeText"></p>
            </popup>
        </div>

        <!-- Implementation der Funktionalitäten -->
        <script>

            // Warten bis die Seite vollständig geladen ist, bevor die Funktionen ausgeführt werden
            $(document).ready(function(){

                 // Klick-Event des Logout-Button
                $('.logout-button').on('click', function() {

                    // Anfrage an den Server senden, um den Benutzer auszuloggen
                    $.post("/logout", function(response) {

                        // Seite neu laden, nach logout
                        window.location.reload();
                    });
                });

                // Funktion des edit button
                $('#edit').on('click', function (){
                    // Einblenden der Buttons für die Bearbeitungsoptionen
                   $('#hinzu').show();
                   $('#delete').show();
                   $('#edit').hide();
                   $('#kommentar').hide();
                });

                // Funktion, des Redner hinzufügen Button
               $('#hinzu').on('click', function (){

                   // Anfrage ans Backend senden
                   $.ajax({
                       url: '/rede',
                       type: 'put',
                       success: function() {
                           console.log(response)
                       },
                       error: function (xhr, status, error) {
                           console.error(error)
                       }
                   });
               });

               // Funktion, des Redner entfernen Buttons
                $('#delete').on('click', function(){

                    // Anfrage an das Backend senden
                    $.ajax({
                        url: '/rede',
                        type: 'put',
                        success: function(){
                            console.log(response)
                        },
                        error: function(xhr, status, error){
                            console.error(error)
                        }
                    })  ;
                });

                // Funktion des kommentar button
                $('#kommentar').on('click', function (){
                    // Einblenden der Buttons zu Kommentar bearbeitungsfunktionen
                   $('#rednerZuKommentar').show();
                   $('#rednerVonKommentar').show();
                   $('#fraktionZuKommentar').show();
                   $('#fraktionVonKommentar').show();
                   $('#kommentar').hide();
                   $('#edit').hide();
                });

                // Funktion des rednerZuKommentar Button
                $('#rednerZuKommentar').on('click', function (){

                    // Anfrage an das Backend senden
                    $.ajax({
                        url: '/rede',
                        type: 'put',
                        success: function() {
                           console.log(response)
                        },
                        error: function (xhr, status, error) {
                           console.error(error)
                        }
                    });
                });

                // Funktion des rednerVonKommentar Button
                $('#rednerVonKommentar').on('click', function (){

                    // Anfrage an das Backend senden
                    $.ajax({
                        url: '/rede',
                        type: 'put',
                        success: function() {
                            console.log(response)
                        },
                        error: function (xhr, status, error) {
                            console.error(error)
                        }
                    });
                });

                // Funktion des fraktionZuKommentar Button
                $('#fraktionZuKommentar').on('click', function (){

                    // Anfrage an das Backend senden
                    $.ajax({
                        url: '/rede',
                        type: 'put',
                        success: function() {
                            console.log(response)
                        },
                        error: function (xhr, status, error) {
                            console.error(error)
                        }
                    });
                });

                 // Funktion des fraktionVonKommentar Button
                $('#fraktionVonKommentar').on('click', function (){

                    // Anfrage an das Backend senden
                    $.ajax({
                        url: '/rede',
                        type: 'put',
                        success: function() {
                            console.log(response)
                        },
                        error: function (xhr, status, error) {
                            console.error(error)
                        }
                    });
                });


                //Funktion reden abrufen
                $('#rede').submit(function (event){

                    // Verhindern, dass die Anfrage per Default gesendet wird.
                   event.preventDefault();

                   // Abrufen der redenId aus dem Eingabefeld
                   var id = $('#redenId').val();

                   // Anfrage an das Backend senden um die Rede aus der MongoDB zu laden.
                   $.ajax({
                        url: '/rede/' + id, // Anhänden der redenId an den Path der API-Methode
                        type: 'get',
                        success: function (response) {
                            console.log("Geladene Rede:", response);
                            $('#redeText').text(response);
                            $('#geladeneRede').show()
                        },
                        error: function (xhd, status, error){
                            console.error(error)
                        }
                    });
                });
            });
        </script>
    </body>
</html>