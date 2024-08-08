__author__ = "Felix_Tams_977844"

a = int(input("Bitte eine natürliche Zahl eingeben "))
b = int(input("Bitte eine natürliche Zahl eingeben "))
while b != 0:
    h = a % b
    a = b
    b = h
    
print("Das Ergebnis lautet: ",a)
