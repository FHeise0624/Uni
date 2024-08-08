<!--
Template für das signup.
@author Felix
//-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Signup</title>

        <!-- Stylesheets einbinden -->
        <link rel="stylesheet" href="/signup.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- jQuery einbinden -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <!-- Inhalt der Seite definieren -->
    <body>
        <div class="container">

            <!-- Dropdownmenü -->
            <div class="dropdown">

                <!-- Dropdown Menü-Button -->
                <button class="dropbtn"><i class="fas fa-bars"></i></button>

                <!-- Inhalt Dropdownmenü -->
                <div class="dropdown-content">
                    <a href="/login">Login</a>
                    <a href="/home">Homepage</a>

                    <!-- Inhalte verfügbar für eingeloggte Nutzer -->
                    <#if isAuthenticatedUser>
                        <a href="/profile"><button>Profil</button></a>
                        <button class="logout-button">Logout</button>
                    </#if>

                    <!-- Inhalte verfügbar für eingeloggte Nutzer der Gruppe Admin -->
                    <#if isAuthenticatedAdmin>
                        <a href="/userManagement"><button>Nutzer Management</button></a>
                    </#if>
                </div>
            </div>

            <!-- Titel der Seite -->
            <h1>Sign up!</h1>

            <!-- Definition Signup Form-->
            <form id="signupForm" action="/postBenutzer" method="post">

                <!-- Abfrage der Nutzerdaten -->
                <div class="form-group">
                    <label for="vorname">Vorname:</label>
                    <input type="text" id="vorname" name="vorname" required>
                </div>
                <div class="form-group">
                    <label for="nachname">Nachname:</label>
                    <input type="text" id="nachname" name="nachname" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="benutzername">Username:</label>
                    <input type="text" id="benutzername" name="benutzername" required>
                </div>
                <div class="form-group">
                    <label for="password">Passwort:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit">Sign Up</button>
            </form>

            <!-- Option weiterleitung zum Login -->
            <p>Du hast bereits einen Account? <a href="/login">Login</a></p>
        </div>

        <!-- Implementation der Funktionalitäten -->
        <script>

            // Warten bis die Seite vollständig geladen ist, bevor die Funktionen ausgeführt werden
            $(document).ready(function() {

                // Funktion die das Signup Formular erzeugt, serialisiert und submitted.
                $('#signupForm').submit(function(event) {
                    event.preventDefault(); // Verhindert, dass das Formular per Default submitted wird.

                    // Serialisierung der Daten in JSON Format.
                    var formData = {
                        benutzername: $('#benutzername').val(),
                        password: $('#password').val(),
                        vorname: $('#vorname').val(),
                        nachname: $('#nachname').val(),
                        email: $('#email').val(),
                        gruppenIds: ["User"] // Default Nutzergruppe = User.
                    };

                    // Senden der POST-Anfrage an das Backend.
                    $.ajax({
                        url: $(this).attr('action'),
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(formData),
                        success: function(response) {
                            console.log(response);

                            // Weiterleitung auf die Login-Seite bei erfolgreicher Registrierung
                            window.location.href = "/login";
                        },
                        // Ausgabe einer Fehlermeldung, wenn die Registrierung nicht möglich ist
                        error: function(xhr, status, error) {
                            console.error(xhr.responseText);
                        }
                    });
                });

                // Klick-Event des Logout-Button
                $('.logout-button').on('click', function() {

                    // Anfrage an den Server senden, um den Benutzer auszuloggen
                    $.post("/logout", function(response) {

                        // Weiterleitung auf die Homepage nach Logout
                        window.location.href=("/home");
                    });
                });
            });
        </script>
    </body>
</html>
