<!--
Template für das Protokoll-Seite.
@author Felix
//-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>

        <!-- Einbinden der Stylesheets -->
        <link rel="stylesheet" href="/protokolle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- Einbinden von jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <!-- Inhalt der Seite definieren -->
    <body>
    <!-- Dropdown Menü definieren -->
        <div class="dropdown">

            <!-- Dropdown-Menü-Button -->
            <button class="dropbtn"><i class="fas fa-bars"></i></button>

            <!-- Inhalt des Dropdown Menüs -->
            <div class="dropdown-content">

                <!-- Inhalt ohne Login verfügbar -->
                <a href="/home"><button>Homepage</button></a>
                <a href="/login">Login</a>
                <a href="/speeches"><button>Reden</button></a>

                <!-- Inhalt für eingeloggte Nutzer verfügbar -->
                <#if isAuthenticatedUser>
                    <button class="logout-button">Logout</button>
                    <a href="/profile"><button>Profil</button></a>
                </#if>

                <!-- Inhalt für eingeloggte Nutzer der Gruppe Manager verfügbar -->
                <#if isAuthenticatedManager>
                    <a href="/abgeordnetenManagement"><button>Abgeordneten Management</button></a>
                </#if>

                <!-- Inhalt für eingeloggte Nutzer der Gruppe Admin verfügbar -->
                <#if isAuthenticatedAdmin>
                    <a href="/userManagement"><button>Nutzer Management</button></a>
                </#if>
            </div>
        </div>

        <!-- Container für Inhalt der Seite -->
        <div class="container">
            <h1>Parlamentsprotoklle</h1>

            <!-- Management Buttons -->
            <#if isAuthenticatedManager>
                <button id="add">Neue Protokolle</button>
                <button id="edit">Protokoll anpassen</button>
            </#if>
        </div>

        <!-- Implementierung der Funktionalitäten -->
        <script>

            // Warten bis Seite vollständig geladen wurde, bevor Funktionen ausgeführt werden
            $(document).ready(function(){

                 // Klick-Event des Logout-Button
                $('.logout-button').on('click', function() {

                    // Anfrage an den Server senden, um den Benutzer auszuloggen
                    $.post("/logout", function(response) {

                        // Seite neu laden, nach logout
                        window.location.reload();
                    });
                });

                // Funktion, des Protokoll hinzufügen Button
                $('#add').on('click', function() {

                    // Anfrage ans Backend zum Anlegen eines neuen Protokolls
                    $.ajax({
                        url: '/plenarprotokolle',
                        type: 'post',
                        success: function(response) {
                            console.log(response)
                        },
                        error: function(xhr, status, error) {
                            console.error(error);
                        }
                    });
                });

                 //Funktion, des Protokoll bearbeiten Button
                $('#edit').on('click', function() {

                    // Anfrage ans Backend zum Bearbeiten des Protokolls
                    $.ajax({
                        url: '',
                        method: '',
                        success: function (){
                            console.log(response)
                        },
                        error: function (xhr, status, error){
                            console.error(error)
                        }
                    });

                });
            });
        </script>
    </body>
</html>