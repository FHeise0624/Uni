__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"
"""
This Program is used to access serval usefull function of the eprtools 
module, by using the explained inputs.
"""

# import of self written eprtools module
from EPR.eprtools import Eprtools

# output of program explanation for the user
print("Hallo Nutzer, durch eine der folgenden Eingaben, "
      "können Sie mit Hilfe dieses Programms, nützliche Funktionen ausführen.")
print()
print("Möchten Sie positive Dezimalzahlen in Binärzahlen umrechnen? "
      "Geben Sie 'Binaer' ein.")
print()
print("Möchten Sie erfahren wie viel Zeit noch bis zur EPI Klausurmverbleibt "
      "Geben Sie 'Timer' ein.")
print()
print("Möchten Sie eine der Kursseiten aufrufen? "
      "Geben Sie 'Course' ein")
print()
print("Möchten Sie ein Passwort in von Ihnen gewüschten länge generieren? "
      "Geben Sie 'Passwort' ein")
print()
call = input("Welche Funktion möchten Sie aufrufen? ")
# function calls depending on the user input.
# Calls Binary calculator
if call == "Binaer":
    x = input("Bitte eine positive Dezimalzahl eingeben!")
    print(Eprtools.decimal_to_binary(x))

# calls the exam timer
elif call == "Timer":
    x = input("Bitte geben Sie das Datum und die Uhrzeit in folgendem "
              "Vormat ein: Jahr Monat Tag Stunden Minuten ")
    print(Eprtools.exam_countdown(x))

# calls the website access tool
elif call == "Course":
    print("Bitte geben Sie einen der folgenden Kurse ein, um die "
          "entsprechende Website aufzufrufen:"
          "EPR, GPR, DisMod, LinADi")
    x = input()
    Eprtools.open_course_page(x)

# calls the password generator
elif call == "Passwort":
    x = int(input("Wie viele Zeichen soll das Passwort haben?: "))
    print(Eprtools.passwort_gen(x))
#