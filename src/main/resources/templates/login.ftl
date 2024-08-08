<!--
Template für den Login.
@author Felix
-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>

        <!-- Einbinden Stylesheets -->
        <link rel="stylesheet" href="/login.css">

        <!-- Einbinden jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <!-- Definition Seiteninhalt -->
    <body>

        <!-- Erstellen Container für den Seiteninhalt -->
        <div class="container">
            <h1>Login</h1>

            <!-- Ausgabe Fehlermeldung -->
            <div id="loginMessage" class="error-message" style="display: none;"></div>

            <!-- Erstellen Login-Formular mit Eingabefeldern -->
            <form id="loginForm" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required><br><br>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required><br><br>
                <button type="submit">Login</button>
            </form>

            <!-- Links auf andere Seiten -->
            <a href="/home" class="continue-as-guest" id="guest">Continue as Guest</a>
            <a href="/signup" id="sign">Sign up</a>
        </div>

        <!-- Implementation von Funktionalitäten -->
        <script>
            <!-- Warten bis die Seite vollständig geladen ist bevor Funktionen ausgeführt werden -->
            $(document).ready(function(){

                // Funktion des Login-Formulars
                $("#loginForm").submit(function (event) {

                    // Verhindern, dass die Anfrage per Default abgeschickt wird
                    event.preventDefault();

                    // Daten aus dem Formular abrufen
                    var formData = {
                        username: $("#username").val(),
                        password: $("#password").val()
                    };

                    // Vorbereiten der URL für die Login-Anfrage
                    var loginUrl = "/login?benutzerId=" + formData.username;

                    // AJAX-Anfrage an die das Backend mittels API
                    $.ajax({
                        type: "POST",
                        url: loginUrl,
                        data: formData,
                        success: function(response) {
                            // Weiterleitung auf die Homepage bei erfolgreichem Login
                            window.location.href="/home";
                        },
                        error: function(xhr, status, error) {
                            // Fehlermeldung wenn der Login nicht möglich ist
                            $("#loginMessage").text("Login failed: Invalid username or password").show();
                        }
                    });
                });
            });
        </script>
    </body>
</html>
