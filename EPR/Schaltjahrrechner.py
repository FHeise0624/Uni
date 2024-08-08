__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"

'''
Programm das eine Jahreszahl entgegennimmt und berechnet ob es sich
dabei um ein Schaltjahr handelt. Das Ergebnis wird ausgegeben.
'''
#set variable for restart loop
c = "JA"
while c == "JA":
    
#input year as a number
    year = (input("Bitte geben Sie eine vierstellige Jahreszahl ein: "))

#check if input consists of digits
    if year.isdigit() == True:
        
#calculation if year is a leap year
        if int(year) % 4 == 0:
            if int(year) % 100 == 0 :
                if int(year) % 400 == 0:
                    print("Das Jahr", year, "ist ein Schaltjahr.")
                else:
                    print("Das Jahr", year, "ist kein Schaltjahr.")
            else:
                print("Das Jahr", year, "ist ein Schaltjahr.")
        else:
            print("Das Jahr", year, "ist kein Schaltjahr.")
            
# possibility  to restart Programm if input was wrong type
    else:
        print("Die Jahreszahl muss eine Ganze Zahl sein")
        c = input("Möchten Sie die Eingabe erneut beginnen? JA oder NEIN ")
    break
