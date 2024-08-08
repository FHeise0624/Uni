<!--
Template für das userManagement.
@author Felix
//-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>

        <!-- Einbinden der Stylesheets -->
        <link rel="stylesheet" href="/userMamagement.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- Einbinden von jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <!-- Seiteninhalt definieren -->
    <body>
        <h1>User Management</h1>

        <!-- Definition des Dropdown-Menüs -->
        <div class="dropdown">

            <!-- Menü-Button -->
            <button class="dropbtn"><i class="fas fa-bars"></i></button>

            <!-- Inhalt des Dropdowns definieren -->
            <div class="dropdown-content">

                <!-- Inhalte ihne Login verfügbar -->
                <a href="/home"><button>Homepage</button></a>
                <a href="/protokolle"><button>Plenarprotokolle</button></a>
                <a href="/speeches"><button>Reden</button></a>

                <!-- Inhalte für eingeloggte Manager verfügbar -->
                <#if isAuthenticatedManager>
                    <a href="/abgeordnetenManagement"><button>Abgeordneten Management</button></a>
                </#if>

                <!-- Inhalte für eingeloggte Nutzer verfügbar -->
                <#if isAuthenticatedUser>
                    <a href="/profile"><button>Profil</button></a>
                    <button class="logout-button">Logout</button>
                </#if>
            </div>
        </div>

        <!-- Definition Container für den Seiteninhalt -->
        <div class="container">

            <!-- Definition der Benutzertabelle -->
            <table>
                <!-- Definition des Tabellenkopfs -->
                <tr>
                    <th>Benutzer ID</th>
                    <th>Benutzername</th>
                    <th>Vorname</th>
                    <th>Nachname</th>
                    <th>E-Mail</th>
                    <th>Nutzergruppe</th>
                    <!-- Zusätzliche Spalten für Managementoptionen für Admins -->
                    <if isAuthenticatedAdmin>
                        <th></th>
                        <th></th>
                    </if>
                </tr>

                <!-- Übernahme der Nutzerdaten aus dem Model (alle Nutzer die in DB existieren), schleife erzeugt
                Zeile für jeden Nutzer
                 -->
                <#list nutzer as user>
                <tr>
                    <td>${user["benutzerId"]}</td>
                    <td>${user["benutzername"]}</td>
                    <td>${user["vorname"]}</td>
                    <td>${user["nachname"]}</td>
                    <td>${user["email"]}</td>
                    <td>
                        <#list user["gruppenIds"] as groupId>
                            ${groupId}<#sep>, </#sep>
                        </#list>
                    </td>

                    <!-- Einfügen Bearbeitungsoption für eingeloggte Admin -->
                    <#if isAuthenticatedAdmin>

                        <!-- Definition der Buttons mit übergabe der Nutzerdaten aus der Spalte als data an den Button
                         -->
                        <td><button class="edit" data-nutzerId="${user["benutzerId"]}" data-vorname="${user["vorname"]}"
                                    data-nachname="${user["nachname"]}" data-email="${user["email"]}" data-benutzername="${user["benutzername"]}" data-password="${user["password"]}">Bearbeiten</button></td>
                        <td><button class="delete" data-nutzerid="${user["benutzerId"]}">Löschen</button></td>
                    </#if>
                </tr>
                </#list>
            </table>

            <!-- Option neue Benutzer anzulegen für eingeloggte Admin -->
            <#if isAuthenticatedAdmin>
                <button class="new">Neuer Nutzer</button>
            </#if>
        </div>

        <!-- Popup zum Anlegen eines Nutzers -->
        <div class="popup-content" id="popupNew" style="display: none;">
            <span class="close" id="closeNew">&times;</span>
            <h2>Neuen Benutzer anlegen</h2>

            <!-- Definition des Signupformulars -->
            <form id="signupForm">
                <label for="vorname">Vorname:</label>
                <input type="text" id="vorname" placeholder="Vorname"><br>
                <label for="nachname">Nachname:</label>
                <input type="text" id="nachname" placeholder="Nachname"><br>
                <label for="email">E-Mail Adresse:</label>
                <input type="email" id="email" placeholder="E-Mail Adresse"><br>
                <label for="benutzername">Benutzername:</label>
                <input type="text" id="benutzername" placeholder="Benutzername"><br>
                <label for="passwort">Passwort:</label>
                <input type="password" id="passwort" placeholder="Passwort"><br>
                <!-- Nutzergruppenwahl -->
                <label for="nutzergruppe">Nutzergruppen:</label><br>
                <input type="checkbox" id="user" name="nutzergruppe" value="User">
                <label for="user">User</label><br>
                <input type="checkbox" id="manager" name="nutzergruppe" value="Manager">
                <label for="manager">Manager</label><br>
                <input type="checkbox" id="admin" name="nutzergruppe" value="Admin">
                <label for="admin">Admin</label><br>
                <button type="submit" id="submitUser">Benutzer anlegen</button>
            </form>
        </div>

        <!-- Popup für die Benutzer bearbeitung -->
        <div class="popup-content" id="editUser">
            <span class="close" id="closeEdit">&times;</span>
            <h2>Benutzer bearbeiten</h2>

            <!-- Definition des Bearbeitungsformulars -->
            <form id="changeForm">
                <input type="hidden" id="edit_benutzerId" value=""> <!-- Hidden field for benutzerId -->
                <label for="edit_vorname">Vorname:</label>
                <input type="text" id="edit_vorname" placeholder="Vorname"><br>
                <label for="edit_nachname">Nachname:</label>
                <input type="text" id="edit_nachname" placeholder="Nachname"><br>
                <label for="edit_email">E-Mail Adresse:</label>
                <input type="email" id="edit_email" placeholder="E-Mail Adresse"><br>
                <label for="edit_benutzername">Benutzername:</label>
                <input type="text" id="edit_benutzername" placeholder="Benutzername"><br>
                <label for="edit_passwort">Passwort:</label>
                <input type="password" id="edit_passwort" placeholder="Passwort"><br>
                <!-- Nutzergruppenwahl -->
                <label for="edit_nutzergruppe">Nutzergruppen:</label><br>
                <input type="checkbox" id="edit_user" name="edit_nutzergruppe" value="User">
                <label for="edit_user">User</label><br>
                <input type="checkbox" id="edit_manager" name="edit_nutzergruppe" value="Manager">
                <label for="edit_manager">Manager</label><br>
                <input type="checkbox" id="edit_admin" name="edit_nutzergruppe" value="Admin">
                <label for="edit_admin">Admin</label><br>
                <button type="submit" id="submitChange">Änderungen speichern</button>
            </form>
        </div>

        <!-- Implementation der Funktionalitäten -->
        <script>

            // Warten, bis die Seite vollständig geladen ist, bevor die Funktionen ausgeführt werden
            $(document).ready(function() {

                // Klick-Event des Logout-Button
                $('.logout-button').on('click', function() {

                    // Anfrage an den Server senden, um den Benutzer auszuloggen
                    $.post("/logout", function(response) {

                        // Weiterleitung auf die Homepage nach erfolgtem Logout
                        window.location.href=("/home");
                    });
                });

                // Klick--Event des Löschen-Buttons
                $('.delete').on('click', function() {
                    var nutzerId = $(this).data('nutzerid'); // Abruf der benutzerId aus den Button-Daten

                    // Anfrage an den Server senden, um benutzer zu löschen.
                    $.ajax({
                       url:"/benutzer/" + nutzerId,
                       method: "DELETE",
                       success: function(response){
                           alert(response);

                           // Neuladen der Seite nach erfolgreichem löschen des Benutzers
                           window.location.reload();
                       },
                       error: function(xhr, status, error) {
                           alert("Fehler beim Löschen des Benutzers: " +xhr.responseText);
                       }
                    });
                });

                // Klick-Event des new Buttons
                $('.new').on('click', function () {
                    $('#popupNew').css('display', 'block'); // Popup anzeigen
                });

                // Funktion zum Schließen des Popups für den neuen Benutzer
                $('#closeNew').on('click', function () {
                    $('#popupNew').css('display', 'none');
                });

                // Funktion die das Signup Formular erzeugt, serialisiert und submitted.
                $('#signupForm').submit(function(event) {
                    event.preventDefault(); // Verhindert, dass das Formular per Default submitted wird.

                    // Serialisierung der Daten in JSON Format.
                    var formData = {
                        benutzername: $('#benutzername').val(),
                        password: $('#passwort').val(),
                        vorname: $('#vorname').val(),
                        nachname: $('#nachname').val(),
                        email: $('#email').val(),
                        gruppenIds: [] // Array für die Nutzergruppen.
                    };

                    // Durchlaufen aller Checkboxen mit dem Namen "nutzergruppe" und Hinzufügen der ausgewählten Werte zum Array.
                    $('input[name="nutzergruppe"]:checked').each(function() {
                        formData.gruppenIds.push($(this).val());
                    });

                    // Senden der POST-Anfrage an das Backend.
                    $.ajax({
                        url: '/postBenutzer',
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(formData),
                        success: function(response) {
                            console.log(response);

                            // Neuladen der Seite nach erfolgreichem anlegen des Benutzers
                            window.location.reload();
                        },
                        error: function(xhr, status, error) {
                            console.error(xhr.responseText);
                        }
                    });
                });

                // Klick-Event, des Bearbeiten Button
                $('.edit').on('click', function () {

                    // Abruf der Nutzerdaten aus der Button data
                    var nutzerId = $(this).data('nutzerid'); // Corrected from 'nutzerId' to 'nutzerid'
                    var vorname = $(this).data('vorname');
                    var nachname = $(this).data('nachname');
                    var email = $(this).data('email');
                    var benutzername = $(this).data('benutzername'); // Corrected from 'nutzername' to 'benutzername'
                    var password = $(this).data('password');

                    // Ersetzen der Platzhalter im Formular mit den Benutzerdaten
                    $('#edit_benutzerId').val(nutzerId);
                    $('#edit_vorname').val(vorname);
                    $('#edit_nachname').val(nachname);
                    $('#edit_email').val(email);
                    $('#edit_benutzername').val(benutzername);
                    $('#edit_passwort').val(password);

                    // Einblenden des Buttons
                    $('#editUser').css('display', 'block');
                });

                // Funkton die das Formular serialisiert und die Anfrage an das Backend sendet
                $('#changeForm').submit(function(event){
                    event.preventDefault(); // Verhindern, dass die Anfrage automatisch versendet wird

                    // Arufen und serialisieren der Benutzerdaten aus dem Formular
                    var changeData={
                        benutzerId: $('#edit_benutzerId').val(),
                        benutzername: $('#edit_benutzername').val(),
                        password: $('#edit_passwort').val(),
                        vorname: $('#edit_vorname').val(),
                        nachname: $('#edit_nachname').val(),
                        email: $('#edit_email').val(),
                        gruppenIds: []
                    }

                    // Einfügen der gewählten Nutzergruppe
                    $('input[name="edit_nutzergruppe"]:checked').each(function(){
                        changeData.gruppenIds.push($(this).val());
                    });

                    // Anfrage an das Backend senden
                    $.ajax({
                        url: '/benutzer',
                        method: 'PUT',
                        contentType: 'application/json',
                        data: JSON.stringify(changeData),
                        success: function(response) {
                            console.log(response);

                            // Ausblenden des Popups und neu laden der Seite
                            $('#editUser').css('display', 'none');
                            window.location.reload();
                        },
                        error: function(xhr, status, error) {
                            console.error(xhr.responseText);
                        }
                    });
                });
            });
        </script>
    </body>
</html>