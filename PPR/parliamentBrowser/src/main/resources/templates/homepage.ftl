<!--
Template für die Homepage
@author Felix
 -->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Homepage</title>

        <!-- Einbinden der Stylesheets -->
        <link rel="stylesheet" href="/homepage.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- Einbinden JQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <!-- Seiteninhalt definieren -->
    <body>

        <!-- Erstellen container, der den gesamten Inhalt der Seite enthält. -->
        <div class="container">
            <h1>Insight Bundestag</h1>

            <!-- Dropdown Menü definieren -->
            <div class="dropdown">

                <!-- Menü-Button -->
                <button class="dropbtn"><i class="fas fa-bars"></i></button>

                <!-- Inhalt des Menüs in abhängigkeit des Authentifizierungsstatus -->
                <div class="dropdown-content">

                    <!-- Inhalt nach Login verfügbar -->
                    <#if !isAuthenticatedUser>
                        <a href="/login">Login</a>
                    </#if>

                    <!-- Inhalt ohne Login verfügbar -->
                    <a href="/protokolle"><button>Plenarprotokolle</button></a>
                    <a href="/speeches"><button>Reden</button></a>

                    <!-- Inhalt für eingeloggte Nutzer der Gruppe Manager verfügbar -->
                    <#if isAuthenticatedManager>
                        <a href="/abgeordnetenManagement"><button>Abgeordneten Management</button></a>
                    </#if>

                    <!-- Inhalt für eingeloggte Nutzer der Gruppe Admin verfügbar -->
                    <#if isAuthenticatedAdmin>
                        <a href="/userManagement"><button>Nutzer Management</button></a>
                    </#if>

                    <!-- Inhalt für alle  eingeloggten Nutzer verfügbar -->
                    <#if isAuthenticatedUser>
                        <a href="/profile"><button>Profil</button></a>
                        <button class="logout-button">Logout</button>
                    </#if>
                </div>
            </div>
        </div>

        <!-- Implementation der Funktionalitäten der Seite -->
        <script>

            // Funktion hinter dem Logout Button
            $(document).ready(function() {

                // Klick-Event des Logout-Button
                $('.logout-button').on('click', function() {

                    // Post-Anfrage an Server mit Seiten-Reload
                    $.post("/logout", function(response) {
                        window.location.reload();
                    });
                });
            });
        </script>
    </body>
</html>
