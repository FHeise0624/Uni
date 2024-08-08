"""
This program takes a String as an Input and performs the defined function
string_info on the input-string.
"""

__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"


# definition of function to check the string and creat output about
# information.
def string_info(st):
    """
    This Function checks a string for length, lowercase letters,
    valid python-identifier, ending 'ing', only lowercase, replace a for e
    :param st: st=str
    :return: output of information
    """
    # length of string.
    print(len(st))

    # check if only letters.
    if st.isalpha():
        print("Der String besteht nur aus Buchstaben.")
    else:
        print("Der String enthaelt auch andere Zeichen als Buchstaben.")

    # check if string is identifier.
    if st.isidentifier():
        print("Der String ist gueltiger Python-Identifier.")
    else:
        print("Der String ist kein gueltiger Python-Identifier.")
    # check for ending.

    if st.endswith("ing"):
        print("Der String endet mit 'ing'.")
    else:
        print("Der String endet nicht mit 'ing'.")

    # check if letters are lowercase.
    if st.islower():
        print("Der String besteht nur aus Kleinbuchstaben.")
    else:
        print("Der String besteht nicht nur aus kleinbuchstaben.")

    # exchange of 'a' for 'e'
    st_n = st.replace("a", "e")
    print(st_n)


a = input("Bitte eingabe taetigen: ")
string_info(a)

# Testcases
if __name__ == "__main__":
    st1 = "Hallo zusammen"
    print("Testcase 1:")
    string_info(st1)
    print()

    st2 = "entertaining"
    print("Testcase 2:")
    string_info(st2)
    print()

    st3 = "numbers 123"
    print("Testcase 3:")
    string_info(st3)
    print()
