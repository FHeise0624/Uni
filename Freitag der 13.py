__author__ = "977844, Tams"

'''Programm, das berechnet, wie viele Freitage der 13. ein Jahr hat,
in Abhängigkeit davon, ob es ein Schaltjahr ist oder nicht und
mit welchem Wochentag das Jahr beginnt.'''

#input year as a number
year = (input("Bitte geben Sie eine vierstellige Jahreszahl ein: "))
    
# definition of leap year funktion
'''Wie eine Funktion definiert wird stammt von
EPR VE02 - Weitere kontrollstrukturen Folie 37'''
def leap_year(year=0):
        
#calculation if year is a leap year
    if int(year) % 4 == 0:
        if int(year) % 100 == 0 :
            if int(year) % 400 == 0:
                return(True)
#returns False if not leap year
            else:
                return(False)
#
            return(True)
#returns False if Year is no leap year
    else:
        return(False)


#dependance on start day
day = input("Mit welchem Tag beginnt das Jahr?")
week = ["Montag", "Dienstag","Mittwoch","Donnerstag",
        "Freitag","Samstag","Sonntag"]

'''match case von EPR VE01 - Die nächsten Schritte Folie 51'''
#calculates the friday 13. for each startind day of year if leap year
if leap_year(year) == True:
    while day == week[0]:
        feb = week[31%7]
        mar = feb[29%7]
        print(feb, mar)
    
    
'''test'''



        
##'''Testcode um zu schauen retunr richtig ist'''
##print(year)
##print(leap_year(year))
##if (leap_year(year) == True):
##    print("Schaltjahr")
##elif (leap_year(year)) == False:
##    print("Kein Schaltjahr")


