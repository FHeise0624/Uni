<!--
Template für das abgeordnetenManagement.
@author Felix
//-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Abgeordneten Management</title>

        <!-- Einbinden der Stylesheets -->
        <link rel="stylesheet" href="/abgeordnetenManagement.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- Einbinden von jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <!-- Inhlat der Seite definieren -->
    <body>
        <h1>Abgeordneten Management</h1>

        <!-- Dropdown -Menü -->
        <div class="dropdown">

            <!-- Dropdown Menü-Button -->
            <button class="dropbtn"><i class="fas fa-bars"></i></button>

            <!-- Inhalt des Dropdowns -->
            <div class="dropdown-content">

                <!-- Inhalt ohne Login verfügbar -->
                <a href="/home"><button>Homepage</button></a>
                <a href="/plenaryProtocols"><button>Plenar Protokolle</button></a>
                <a href="/reden"><button>Reden</button></a>

                <!-- Inhalt für eingeloggte Nutzer verfügbar -->
                <#if isAuthenticatedUser>
                    <a href="/profile"><button>Profil</button></a>
                    <button class="logout-button">Logout</button>
                </#if>

                <!-- Inhlate für eingeloggte Admin verfügbar -->
                <#if isAuthenticatedAdmin>
                    <a href="/userManagement"><button>Nutzer Management</button></a>
                </#if>
            </div>
        </div>

        <!-- Definition Container für die Abgeordneten-Tabelle -->
        <div class="container">

            <!-- Definition der Abgeordneten-Tabelle -->
            <table class="custom-table">

                <!-- Definition des Tabellenkopfes -->
                <tr>
                    <th>Abgeordneten ID</th>
                    <th>Adelssuffix</th>
                    <th>Akademischer Titel</th>
                    <th>Anrede</th>
                    <th>Vorname</th>
                    <th>Nachname</th>
                    <th>Geschlecht</th>
                    <th>Geburtsdatum</th>
                    <th>Geburtsort</th>
                    <th>Sterbedatum</th>
                    <th>Beruf</th>
                    <th>Religion</th>
                    <th>Partei</th>
                    <th>Mandate</th>
                    <th>Mitgliedschaften</th>
                    <th>Ortszusatz</th>
                    <th>Reden IDs</th>
                    <th>Vita</th>

                    <!-- Zusätzliche Spalte für Bearbietungsoptionen für Admin-Nutzer -->
                    <#if isAuthenticatedAdmin>
                        <th></th>
                    </#if>
                </tr>

                <!-- Übernahme der Abgeordnetendaten aus dem Model -->
                <#list abgeordnete as abg>
                    <#assign timestamp1 = abg["geburtsDatum"]>
                    <#assign gebDatum = timestamp1?number_to_date>

                    <!-- Fehler abfangen, wenn kein sterbe Datum existiert & Formatieren des Timestamp in (dd.MM
                    .yyyy) -->
                    <#if abg["sterbeDatum"]??>
                        <#assign timestamp2 = abg["sterbeDatum"]>
                        <#assign sterbeDatum = timestamp2?number_to_date>
                    </#if>
                    <#assign dateFormat = "dd.MM.yyyy">

                <!-- Erzeugen einer Zeile für jeden Abgeordneten mit den Daten aus dem Modell -->
                <tr>
                    <td>${abg["abgeordneterId"]}</td>
                    <td>${abg["adelssuffix"]}</td>
                    <td>${abg["akadTitel"]}</td>
                    <td>${abg["anrede"]}</td>
                    <td>${abg["vorname"]}</td>
                    <td>${abg["name"]}</td>
                    <td>${abg["geschlecht"]}</td>
                    <td>${gebDatum?string(dateFormat)}</td> <!-- Datum formatieren -->
                    <td>${abg["geburtsOrt"]}</td>
                    <td>
                        <#if abg["sterbeDatum"]??> <!-- Fehler abfangen, wenn kein sterbe Datum existiert -->
                            ${sterbeDatum?string(dateFormat)}
                        </#if>
                    </td>
                    <td>${abg["beruf"]}</td>
                    <td>${abg["religion"]}</td>
                    <td>${abg["parteiId"]}</td>
                    <!-- Auflisten der Mandate (extrahieren der Daten aus dem Mandate Objekt -->
                    <td>
                        <#if abg.mandate??>
                            <#list abg.mandate as mandat>
                                <#if mandat.typ??>
                                    Typ: ${mandat.typ}<br>
                                </#if>
                                <#if mandat.wahlperiodeId??>
                                    Wahlperioden ID: ${mandat.wahlperiodeId}<br>
                                </#if>
                                <#if mandat.wahlkreisId??>
                                    Wahlkreis ID: ${mandat.wahlkreisId}<br>
                                </#if>
                            </#list>
                        </#if>
                    </td>
                    <!-- Auflisten der Mitgliedschaften (extrahieren der Daten aus dem Mitgliedschaften Objekt -->
                    <td>
                        <#if abg.mitgliedschaften??>
                            <#list abg.mitgliedschaften as membership>
                                <#if membership.gruppe??>
                                    <#if membership.gruppe.typ == "Fraktion_Gruppe">
                                       <b>Fraktion/Gremium: </b> ${membership.gruppe.name}<br>
                                        <#else> <b>Gruppe</b> ${membership.gruppe.name}<br>
                                    </#if>
                                    <#if membership.startDate??>
                                        <b>Startdatum: </b> ${membership.startDate?number_to_date?string(dateFormat)}<br>
                                    </#if>
                                    <#if membership.endDate??>
                                        <b>Enddatum: </b> ${membership.endDate?number_to_date?string(dateFormat)}<br>
                                    </#if>
                                </#if>
                            </#list>
                        </#if>
                    </td>
                    <td>${abg["ortszusatz"]}</td>
                    <td>
                        <#list abg["redenIds"] as reden> <!-- Auflisten der Reden IDs-->
                            ${reden} <#sep>, </#sep>
                        </#list>
                    </td>
                    <td>${abg["vita"]}</td>

                    <!--Mandate in String konvertieren zur Übergabe an Button -->
                    <#assign mandateString = ""?string> <!-- Initialisieren des Strings -->
                        <#if abg.mandate??> <!-- Überprüfen, ob Mandate vorhanden sind -->
                            <#list abg.mandate as mandate> <!-- Iteration über die Mandate -->
                                <#if mandate.typ??> <!-- Überprüfen, ob der Typ vorhanden ist -->
                                    <#assign mandateString = mandateString + "Typ: " + mandate.typ + ", "> <!-- Hinzufügen des Typs -->
                                </#if>
                                <#if mandate.wahlperiodeId??> <!-- Überprüfen der Wahlperiode -->
                                    <#assign mandateString = mandateString + "Wahlperiode ID: " + mandate.wahlperiodeId + ", "> <!-- Hinzufügen der Wahlperiode -->
                                </#if>
                                <#if mandate.wahlkreisId??> <!-- Überprüfen des Wahlkreises -->
                                    <#assign mandateString = mandateString + "Wahlkreis ID: " + mandate.wahlkreisId + ", "> <!-- Hinzufügen des Wahlkreises -->
                                </#if>
                                <#if mandate.startDate??> <!-- Überprüfen des Startdatums -->
                                    <#assign mandateString = mandateString + "Startdatum: " + mandate.startDate?number_to_date?string(dateFormat) + ", "> <!-- Hinzufügen des Startdatums -->
                                </#if>
                                <#if mandate.endDate??> <!-- Überprüfen des Enddatums -->
                                    <#assign mandateString = mandateString + "Enddatum: " + mandate.endDate?number_to_date?string(dateFormat) + ", "> <!-- Hinzufügen des Enddatums -->
                                </#if>
                            </#list>
                        </#if>

                    <!-- Mitgliedschaften in String konvertieren zur übergabe an den Button -->
                    <#assign mitgliedschaftenString = ""?string> <!-- Initialisieren des Strings -->
                        <#if abg.mitgliedschaften??> <!-- Überprüfen, ob Mitgliedschaften vorhanden sind -->
                            <#list abg.mitgliedschaften as membership> <!-- Iteration über die Mitgliedschaften -->
                                <#if membership.gruppe??> <!-- Überprüfen, ob die Gruppe vorhanden ist -->
                                    <#if membership.gruppe.typ == "Fraktion_Gruppe"> <!-- Überprüfen des Gruppentyps -->
                                        <#assign mitgliedschaftenString = mitgliedschaftenString + "Fraktion/Gremium: " + membership.gruppe.name + ", "> <!-- Hinzufügen der Mitgliedschaft -->
                                    <#else>
                                        <#assign mitgliedschaftenString = mitgliedschaftenString + "Gruppe: " + membership.gruppe.name + ", "> <!-- Hinzufügen der Mitgliedschaft -->
                                    </#if>
                                    <#if membership.startDate??> <!-- Überprüfen des Startdatums -->
                                        <#assign mitgliedschaftenString = mitgliedschaftenString + "Startdatum: " + membership.startDate?number_to_date?string(dateFormat) + ", "> <!-- Hinzufügen des Startdatums -->
                                    </#if>
                                    <#if membership.endDate??> <!-- Überprüfen des Enddatums -->
                                        <#assign mitgliedschaftenString = mitgliedschaftenString + "Enddatum: " + membership.endDate?number_to_date?string(dateFormat) + ", "> <!-- Hinzufügen des Enddatums -->
                                    </#if>
                                </#if>
                            </#list>
                        </#if>

                    <#-- Konvertieren der RedenIDs in String zur übergabe an den Button -->
                    <#assign redenIdsString = "">
                    <#list abg.redenIds as id>
                        <#if redenIdsString?length != 0>
                            <#assign redenIdsString = redenIdsString + ", ">
                        </#if>
                        <#assign redenIdsString = redenIdsString + id>
                    </#list>

                    <!--Einfügen Bearbeitungsoption für Manager-Nutzer -->
                    <#if isAuthenticatedManager>

                        <!-- Übergabe der Abgeordnetendaten an den Button -->
                       <td><button class="edit"
                                   data-abgeordnetenId="${abg["abgeordneterId"]}"
                                   data-adelssuffix="${abg["adelssuffix"]}"
                                   data-akadTitel="${abg["akadTitel"]}"
                                   data-anrede="${abg["anrede"]}"
                                   data-vorname="${abg["vorname"]}"
                                   data-nachname="${abg["name"]}"
                                   data-geschlecht="${abg["geschlecht"]}"
                                   data-geburtsdatum="${gebDatum?string(dateFormat)}"
                                   data-geburtsort="${abg["geburtsOrt"]}"
                                   <#if abg["sterbeDatum"]??>
                                       data-sterbedatum="${sterbeDatum?string(dateFormat)}"
                                       <#else>
                                       data-sterbedatum="n/a"
                                   </#if>
                                   data-beruf="${abg["beruf"]}"
                                   data-religion="${abg["religion"]}"
                                   data-partei="${abg["parteiId"]}"
                                   data-mandate="${mandateString}"
                                   data-mitgliedschaften="${mitgliedschaftenString}"
                                   data-ortszusatz="${abg["ortszusatz"]}"
                                   data-redenIds="${redenIdsString}"
                                   data-vita="${abg["vita"]}"
                           >Bearbeiten</button></td>
                    </#if>
                </tr>
                </#list>
            </table>
        </div>

        <!-- Popup zu bearbeitung von Abgeordneten -->
        <div class="popup-content" id="editAbg">
            <span class="close" id="closeEdit"></span>
            <h2>Abgeordneten bearbeiten</h2>

            <!-- Definition des Formulars zur bearbeitung eines Abgeordneten -->
            <form id="changeForm">
                <label for="edit_abgeordnetenId">Abgeordneten ID:</label>
                <input type="text" id="edit_abgeordnetenId" placeholder="Abgeordneten ID"><br>
                <label for="edit_adelssuffix">Adelssuffix:</label>
                <input type="text" id="edit_adelssuffix" placeholder="Adelssuffix"><br>
                <label for="edit_akadTitel">Akad. Titel:</label>
                <input type="text" id="edit_akadTitel" placeholder="Akad. Titel"><br>
                <label for="edit_anrede">Anrede:</label>
                <input type="text" id="edit_anrede" placeholder="Anrede"><br>
                <label for="edit_vorname">Vorname:</label>
                <input type="text" id="edit_vorname" placeholder="Vorname"><br>
                <label for="edit_nachname">Nachname:</label>
                <input type="text" id="edit_nachname" placeholder="Nachname"><br>
                <label for="edit_geschlecht">Geschlecht:</label>
                <input type="text" id="edit_geschlecht" placeholder="Geschlecht"><br>
                <label for="edit_geburtsdatum">Geburtsdatum:</label>
                <input type="text" id="edit_geburtsdatum" placeholder="Geburtsdatum"><br>
                <label for="edit_geburtsort">Geburtsort:</label>
                <input type="text" id="edit_geburtsort" placeholder="Geburtsort"><br>
                <label for="edit_sterbedatum">Sterbedatum:</label>
                <input type="text" id="edit_sterbedatum" placeholder="Sterbedatum"><br>
                <label for="edit_beruf">Beruf:</label>
                <input type="text" id="edit_beruf" placeholder="Beruf"><br>
                <label for="edit_religion">Religion:</label>
                <input type="text" id="edit_religion" placeholder="Religion"><br>
                <label for="edit_partei">Partei:</label>
                <input type="text" id="edit_partei" placeholder="Partei"><br>
                <label for="edit_mandate">Mandate:</label>
                <input type="text" id="edit_mandate" placeholder="Mandate"><br>
                <label for="edit_mitgliedschaften">Mitgliedschaften:</label>
                <input type="text" id="edit_mitgliedschaften" placeholder="Mitgliedschaften"><br>
                <label for="edit_ortszusatz">Ortszusatz:</label>
                <input type="text" id="edit_ortszusatz" placeholder="Ortszusatz"><br>
                <label for="edit_redenIds">Reden IDs:</label>
                <input type="text" id="edit_redenIds" placeholder="Reden IDs"><br>
                <label for="edit_vita">Vita:</label>
                <input type="text" id="edit_vita" placeholder="Vita"><br>
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

                // Klick-Event des Bearbeiten-Button
                 $(document).on('click', '.edit', function() {

                     // Abrufen der Daten aus dem Button
                    var abgeordnetenId = $(this).data('abgeordnetenid');
                    var adelssuffix = $(this).data('adelssuffix');
                    var akadTitel = $(this).data('akadtitel');
                    var anrede = $(this).data('anrede');
                    var vorname = $(this).data('vorname');
                    var nachname = $(this).data('nachname');
                    var geschlecht = $(this).data('geschlecht');
                    var geburtsdatum = $(this).data('geburtsdatum');
                    var geburtsort = $(this).data('geburtsort');
                    var sterbedatum = $(this).data('sterbedatum');
                    var beruf = $(this).data('beruf');
                    var religion = $(this).data('religion');
                    var partei = $(this).data('partei');
                    var mandate = $(this).data('mandate');
                    var mitgliedschaften = $(this).data('mitgliedschaften');
                    var ortszusatz = $(this).data('ortszusatz');
                    var redenIds = $(this).data('redenids');
                    var vita = $(this).data('vita');

                    // Überschreiben der Platzhalter im Formular mit den Daten aus dem Button
                    $('#edit_abgeordnetenId').val(abgeordnetenId);
                    $('#edit_adelssuffix').val(adelssuffix);
                    $('#edit_akadTitel').val(akadTitel);
                    $('#edit_anrede').val(anrede);
                    $('#edit_vorname').val(vorname);
                    $('#edit_nachname').val(nachname);
                    $('#edit_geschlecht').val(geschlecht);
                    $('#edit_geburtsdatum').val(geburtsdatum);
                    $('#edit_geburtsort').val(geburtsort);
                    $('#edit_sterbedatum').val(sterbedatum);
                    $('#edit_beruf').val(beruf);
                    $('#edit_religion').val(religion);
                    $('#edit_partei').val(partei);
                    $('#edit_mandate').val(mandate);
                    $('#edit_mitgliedschaften').val(mitgliedschaften);
                    $('#edit_ortszusatz').val(ortszusatz);
                    $('#edit_redenIds').val(redenIds);
                    $('#edit_vita').val(vita);

                    // Einblenden des Edit Button im Formular
                    $('#editAbg').css('display', 'block');
                });

                 // Submit Funktion des Formulars bei Klicken auf den Änderungen speichern Button
                $(document).on('submit', '#changeForm', function(event) {
                    event.preventDefault(); // Verhindern, dass das Formular per Default abgesendet wird

                    // Abrufen der Daten aus dem Formular und serialisieren in JSON-Format
                    var changeData={
                        abgeordneterId: $('#edit_abgeordnetenId').val(),
                        adelssuffix: $('#edit_adelssuffix').val(),
                        akadTitel: $('#edit_akadTitel').val(),
                        anrede: $('#edit_anrede').val(),
                        vorname: $('#edit_vorname').val(),
                        name: $('#edit_nachname').val(),
                        geschlecht: $('#edit_geschlecht').val(),
                        geburtsDatum: new Date($('#edit_geburtsdatum').val()).getTime(), // Datum in Timestamp
                        // formatieren
                        geburtsOrt: $('#edit_geburtsort').val(),
                        sterbeDatum: new Date($('#edit_sterbedatum').val()).getTime(), // Datum in Timestamp formatieren
                        beruf: $('#edit_beruf').val(),
                        religion: $('#edit_religion').val(),
                        parteiId: $('#edit_partei').val(),
                        // Umwandeln vom Mandate String ein Mandate Object wie es vom Backend verarbeitet werden kann
                        mandate: $('#edit_mandate').val().split(',').map(function(item) {
                            var mandatData = item.trim().split('/');
                            return {
                                typ: mandatData[1],
                                wahlperiodeId: mandatData[0],
                                wahlkreisId: null,
                                abgeordneterId: $('#edit_abgeordnetenId').val()
                            };
                        }),
                        // Umwandeln vom Mitgliedschaften String ein Mitgliedschaften Object wie es vom Backend
                        // verarbeitet werden kann
                        mitgliedschaften: $('#edit_mitgliedschaften').val().split(',').map(function(item) {
                            return {
                                funktion: item.trim(),
                                startDate: null, // Fügen Sie hier das entsprechende Startdatum ein, falls verfügbar
                                endDate: null, // Fügen Sie hier das entsprechende Enddatum ein, falls verfügbar
                                abgeordneterId: null, // Fügen Sie hier die entsprechende Abgeordneten-ID ein, falls verfügbar
                                wahlperiodeId: null, // Fügen Sie hier die entsprechende Wahlperioden-ID ein, falls verfügbar
                                gruppe: null // Fügen Sie hier die entsprechende Gruppe ein, falls verfügbar
                            };
                        }),
                        ortszusatz: $('#edit_ortszusatz').val(),
                        redenIds: $('#edit_redenIds').val(),
                        vita: $('#edit_vita').val()
                    }

                    // Anfrage ans Backend senden um die Abgeordneten Daten zu aktualisieren
                    $.ajax({
                        url: '/abgeordneter', // Update the URL to match the endpoint for updating abgeordneten
                        method: 'PUT',
                        contentType: 'application/json',
                        data: JSON.stringify(changeData),
                        success: function(response) {
                            console.log(response);
                            // Popup schließen und Seite neu laden
                            $('#editAbg').css('display', 'none');
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
