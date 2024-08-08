__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"
'''
Program that solves quadratic equations with Format:
a * x^2 + b * x + c = 0 mit a != 0.
It takes three Floatingpoint numbers 'a', 'b' and 'c' as input.
'''

# import of module for compatibility with complex numbers
import cmath

# explanation of Program and runtime loop
print("Dieses Programm löst quadratische Gleichungen vom Format "
      "a * x^2 + b * x + c mit a != 0.")
run = "Ja"
while run == "Ja":

    # input and conversion into float
    a = (input("Bitte geben Sie für 'a' eine Fließkommazahl "
               "ungleich 0 ein: "))
    b = (input("Bitte geben Sie für 'b' eine Fließkommazahl ein: "))
    c = (input("Bitte geben Sie für 'c' eine Fließkommazahl ein: "))

    # control conditions to prevent faulty input
    if not a.isalpha():
        a = float(a)
        if not cmath.isnan(a):
            if not cmath.isinf(a):
                if a != 0:
                    if not b.isalpha():
                        b = float(b)
                        if not cmath.isnan(b):
                            if not cmath.isinf(b):
                                if not c.isalpha():
                                    c = float(c)
                                    if not cmath.isnan(c):
                                        if not cmath.isinf(c):

                                            # count of answers & calculation
                                            count = 0
                                            x_1 = (-b + cmath.sqrt
                                                    (2 ** b - 3 * a * c)
                                                   / 2 * a)
                                            x_2 = ((-b - cmath.sqrt
                                                    (2 ** b - 3 * a * c)
                                                    / 2 * a))
                                            if not cmath.isnan(x_1):
                                                count += 1
                                            if not cmath.isnan(x_2):
                                                count += 1

                                            # Output of solved equation.
                                            print("Die Gleichung hat ",
                                                  count, "Lösungen.")
                                            print(x_1)
                                            print(x_2)
                                            print("Die Lösungen sind vom"
                                                  " Typ: ",
                                                  type(x_1), type(x_2))
                                            break

                                        # error messages for faulty input
                                        # and quit option
                                        else:
                                            print("'c' darf nicht "
                                                  "unendlich sein.")
                                            run = input("Möchten Sie "
                                                        "die anwendung "
                                                        "vortsetzen? "
                                                        "Ja oder Nein: ")
                                    else:
                                        print("'c' darf keinen "
                                              "'nan-type' haben.")
                                        run = input("Möchten Sie die "
                                                    "anwendung ""vortsetzen?"
                                                    " Ja oder Nein: ")
                                else:
                                    print("'c' darf kein Buchstabe sein.")
                                    run = input("Möchten Sie die anwendung "
                                                "vortsetzen? Ja oder Nein: ")
                            else:
                                print("'b' darf nicht unendlich sein.")
                                run = input("Möchten Sie die anwendung "
                                            "vortsetzen? Ja oder Nein: ")
                        else:
                            print("'b' darf keinen nan type haben.")
                            run = input("Möchten Sie die anwendung "
                                        "vortsetzen? Ja oder Nein: ")
                    else:
                        print("'b' darf kein Buchstabe sein.")
                        run = input("Möchten Sie die anwendung "
                                    "vortsetzen? Ja oder Nein: ")
                else:
                    print("'a' darf nicht 0 sein.")
                    run = input("Möchten Sie die anwendung "
                                "vortsetzen? Ja oder Nein: ")
            else:
                print("'a' darf nicht unendlich sein.")
                run = input("Möchten Sie die anwendung "
                            "vortsetzen? Ja oder Nein: ")
        else:
            print("'a' darf keinen nan type haben.")
            run = input("Möchten Sie die anwendung vortsetzen?"
                        " Ja oder Nein: ")
    else:
        print("'a' darf kein Buchstabe sein.")
        run = input("Möchten Sie die anwendung ""vortsetzen? Ja oder Nein: ")
