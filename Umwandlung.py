"""
This program takes the numbers 1 - 9 in writen form(german) as an input,
converts them into their integer equivalent and prints them out on the console.
"""
__author__ = "977844, Tams"
__email__ = "s5668531@stud.uni-frankfurt.de"

numbers = {"eins": 1, "zwei": 2, "drei": 3, "vier": 4, "fünf": 5, "sechs":
           6, "sieben": 7, "acht": 8, "neun": 9}
x = []

a = input("Möchten Sie eine Zahl zwischen 1 & 9 wie folgt eingeben?:\n'eins', "
          "'zwei', 'drei', 'vier', 'fünf', 'sechs', 'sieben', 'acht', 'neun'\n"
          "Ja oder Nein\n")
while a == "Ja":
    x.append(input("Bitte geben Sie eine der Zahlen ein:\n"))
    a = input("Möchten Sie eine weitere Zahl eingeben? Ja oder Nein\n")

print("Sie haben folgende Zahlen eingegeben: ", x)

i = 0
l = []
while i in range(0, (len(x) - 1)):
    if x[i] == "eins":
        l.append(numbers["eins"])
    elif x[i] == "zwei":
        l.append(numbers["zwei"])
    elif x[i] == "drei":
        l.append(numbers["drei"])
    elif x[i] == "vier":
        l.append(numbers["vier"])
    elif x[i] == "fünf":
        l.append(numbers["fünf"])
    elif x[i] == "sechs":
        l.append(numbers["sechs"])
    elif x[i] == "sieben":
        l.append(numbers["sieben"])
    elif x[i] == "acht":
        l.append(numbers["acht"])
    elif x[i] == "neun":
        l.append(numbers["neun"])
print(l)
