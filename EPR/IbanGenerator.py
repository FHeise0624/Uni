__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"

'''Programm das aus dem Laendercode, der Bankleitzzahl und der Kontonummer
eine IBAN inkl. Pruefsumme generiert, ausgibt und erklaert'''

y = "JA"
while y == "JA":
    

    #input
    country = input("Bitte den Laendercode Ihrer Bank in Grossbuchstaben eingeben.")
    bank = input("Bitte die Bankleitzahl eingeben. ")
    account = input("Bitte die Kontonummer eingeben. ")
    controll = "00"

    #processing
    #Laedercode in Zahlen uebersetzen
    '''Diese Code sequenz stammt von
    https://www.delftstack.com/de/howto/python/comvert-letter-to-number-python/'''
    #ubersetzen der Buchstaben in ihre positionen im Aplhabet
    n = []
    for x in country:
        n.append(ord(x)-64)

    '''ab hier stammt der code erneut von mir'''
    p1 = n[0] + 9 
    p2 = n[1] + 9 

    # abfrage ob die Eingabe des Laendercodes in Grossbuchstaben erfolg ist.
    # 10 & 35 sind die Grenzen der Uebersetzung der Buchstaben in Zahlen
    if (10 <= p1 <= 35) & (10 <= p2 <= 35):
        translation = str(p1) + str(p2)

#berechnen der Pruefsumme
        controll = 98 - (int(bank + account + translation + controll) % 97)

        #generieren der IBAN
        IBAN = country + str(controll) + " " + bank + " " + account

        #ausgeben der IBAN inkl. Pruefnummer
        print("Ihre IBAN mit der zweistelligen Pruefnummer lautet: ", IBAN)
        break

    else:
        print("Fehler: Der Laendercode besteht nicht aus Grossbuchstaben!")
        y = input("Moechten Sie die Eingabe neustarten? Bitte JA oder NEIN eingeben: ")
