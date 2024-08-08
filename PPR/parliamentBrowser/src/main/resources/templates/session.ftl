<!--
Template für die Session-Seite
@author Felix
-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sitzungen</title>

        <!-- Stylesheets einbinden -->
        <link rel="stylesheet" href="/session.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- jQuery einbinden -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <!-- Seiteninhalt definieren -->
    <body>

        <!-- Container für Inhalt der Seite definieren -->
        <div class="container">
            <h1>Insight Bundestag</h1>

            <!-- Dropdown Menü -->
            <div class="dropdown">

                <!-- Dropdown-Menü-Button -->
                <button class="dropbtn"><i class="fas fa-bars"></i></button>

                <!-- Inhalt des Dropdown Menüs -->
                <div class="dropdown-content">

                    <!-- Inhalt ohne Login verfügbar -->
                    <a href="/home"><button>Homepage</button></a>

                    <!-- Inhalt nur ohne Login verfügbar -->
                    <#if !isAuthenticated>
                        <a href="/login"><button>Login</button></a>
                    </#if>

                    <!-- Inhalt ohne Login verfügbar -->
                    <a href="/protokolle"><button>Plenarprotokolle</button></a>
                    <a href="/speeches"><button>Reden</button></a>

                    <!-- Inhalte für eingeloggte Nutzer verfügbar -->
                    <#if isAuthenticatedUser>
                        <button class="logout-button">Logout</button>
                        <a href="/profile"><button>Profil</button></a>
                    </#if>

                    <!-- Inhalte für eingeloggte Nutzer der Gruppe Manager verfügbar -->
                    <#if isAuthenticatedManager>
                        <a href="/abgeordnetenManagement"><button>Abgeordneten Management</button></a>
                    </#if>

                    <!-- Inhalte für eingeloggte Nutzer der Gruppe Admin verfügbar -->
                    <#if isAuthenticatedAdmin>
                        <a href="/userManagement"><button>Nutzer Management</button></a>
                    </#if>
                </div>
            </div>

            <!-- Optionen, des Sitzungs-Management für eingeloggte Nutzer einblenden -->
            <#if isAuthenticatedUser>
                <button id="editSitzung">Parlamentssitzung bearbeiten</button>
                <button id="editTop" style="display: none">TOPs bearbeiten</button>
                <button id="editSpeech" style="display: none">Reden bearbeiten</button>
                <button id="addSpeech" style="display: none">Rede hinzufügen</button>
            </#if>

            <!-- Optionen, des Sitzungs-Management für eingeloggte Manager einblenden -->
            <#if isAuthenticatedManager>
                <button id="sitzungEntf" style="display: none">Sitzung löschen</button>
                <button id="addSitzung" style="display: none">Sitzung erstellen</button>
                <button id="template" style="display: none">Export-Template bearbeiten</button>
            </#if>
        </div>

        <!-- Implementierung der Funktionalitäten -->
        <script>

            // Warten bis die Seite vollständig geladen ist, bevor Funktionen ausgeführt werden
            $(document).ready(function (){

                // Klick-Event des Logout-Button
                $('.logout-button').on('click', function() {

                     // Anfrage an den Server senden, um den Benutzer auszuloggen
                    $.post("/logout", function(response) {

                         // Seite neu laden nach erfolgreichem Logout
                        window.location.reload();
                    });
                });

                //Funktion des editSitzung button
                $('#editSitzung').on('click', function (){

                    // Einblenden der Bearbeitungsoptionen
                    $('#editTop').show();
                    $('#editSpeech').show();
                    $('#addSpeech').show();
                    $('#addSitzung').show();
                    $('#sitzungEntf').show();
                    $('#template').show();
                    $('#editSitzung').hide();
                });

                //Funktion des editTop button
                $('#editTop').on('click', function (){

                    // Anfrage an das Backend senden
                   $.ajax({
                      url: '/sitzung',
                      type: 'put',
                      success: function (){
                          console.log(response)
                      },
                       error: function (xhr, status, error) {
                          console.error(error)
                       }
                   });
                });

                //Funktion des editSpeech Button
                $('#editSpeech').on('click', function (){

                    // Anfrage an das Backend senden
                    $.ajax({
                        url: '/sitzung',
                        type: 'put',
                        success: function (){
                            console.log(response)
                        },
                        error: function (xhr, status, error){
                            console.error(error)
                        }
                    })
                });

                //Funktion des addSpeech Button
                $('#addSpeech').on('click', function (){

                    // Anfrage an das Backend senden
                   $.ajax({
                       url: '/rede',
                       type: 'post',
                       success: function (){
                           console.log(response)
                       },
                       error: function (xhr, status, error){
                           console.error(error)
                       }
                   });
                });

                //Funktion des addSitzung Button
                $('#addSitzung').on('click', function(){

                    // Anfrage an das Backend senden
                   $.ajax({
                       url: '/sitzung',
                       type: 'post',
                       success: function(){
                           console.log(response)
                       },
                       error: function (xhr, status, error){
                           console.error(error)
                       }
                   });
                });

                //Funktion des sitzungEntf Button
                $('#sitzungEntf').on('click', function (){

                    // Anfrage an das Backend senden
                    $.ajax({
                        url: '/sitzung/:wahlperiode/:nummer',
                        type: 'delete',
                        success: function (){
                            console.log(response)
                        },
                        error: function (xhr, status, error){
                            console.error(error)
                        }
                    });
                });

                //Funktion des template Button
                $('#template').on('click', function (){

                    // Anfrage an das Backend senden
                    $.ajax({
                       url: '',
                       type: '',
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