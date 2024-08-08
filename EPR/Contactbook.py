"""
This Program is a contact book, sorted in Lists for names, addresses,
email-address.
"""
__author__ = "977844, Tams"
__email__ = "s5668531@stud.uni-frankfurt.de"


# Function that adds a new contact(a dictionary) to a contact book(list)
def add_contact(contact_book, add_name="unknown", add_phone="unknown",
                add_address="unknown", add_email="unknown"):
    """
    Takes in a list of contacts, and information strings, creates a contact
    dictionary and add ist to the list. Returns the contact list.
    :param contact_book: list , of contacts (default "unknown").
    :param add_name: str, name of the contact (default "unknown").
    :param add_phone: str, phone number of contact (default "unknown").
    :param add_address: str, address of contact (default "unknown").
    :param add_email: str, email address of contact (default "unknown").
    :return: contact_book
    """
    # crates a dictionary for the contact information and a dictionary that
    # links a contact name with that information, adds contact to contact list.
    info = {"address": add_address, "email": add_email, "phone": add_phone}
    contact = {"name": add_name, "info": info}
    contact_book.append(contact)
    return contact_book


# function to remove a contact from a contactlist, with all information
def del_contact(contact_book, del_name):
    """
    Takes a contact list and contact name and removes this contact from the
    list. Returns the edited list of contacts.
    :param contact_book: list, of contacts (not allowed to be empty)
    :param del_name: str, contact name (key for contact dictionary
    :return: contact_book, list of contacts
    """
    # checks if contact is in list and removes it.
    for i in range(0, len(contact_book)):
        if del_name == contact_book[i-1]["name"]:
            contact_book.pop(i-1)
        i += 1
    return contact_book


# function to edit the information stored in the information dictionary of
# a contact in a contact list.
def edit_contact(contact_book, edit_name, edit, new_inf):
    """
    Takes list of contacts, contact name, information which should be edited.
    Returns contact list with edited contact information.
    :param contact_book: list, of contacts
    :param edit_name: str, name of contact(dict key)
    :param edit: str, information which should be edited (dict key)
    :param new_inf: str, new information
    :return: contact_book, list of contacts with edited information.
    """
    # checks if contact is in list, calls information and changes it
    for j in range(0, len(contact_book)):
        if edit_name == contact_book[j-1]["name"]:
            if edit == "phone":
                contact_book[j-1]["info"]["phone"] = new_inf
            elif edit == "email":
                contact_book[j-1]["info"]["email"] = new_inf
            elif edit == "address":
                contact_book[j-1]["info"]["address"] = new_inf
    return contact_book


# function that lists all the contacts in a contact list.
def list_contacts(contact_book):
    """
    Takes list of contacts and prints out names of all contacts on console.
    :param contact_book: list of contacts.
    :return: print out of all contact names.
    """
    for k in range(0, len(contact_book)):
        print(contact_book[k-1]["name"])
        k += 1


# function that shown all information of one chosen contact in a contact list.
def show_contact(contact_book, show_name):
    """
    Takes contact list and contact name, output of contact information for
    this contact on console.
    :param contact_book: list, contacts.
    :param show_name: str, contact name(dict, key).
    :return: output of contact information on console.
    """
    # prints all information underneath each other.
    for o in range(0, len(contact_book)):
        if show_name == contact_book[o - 1]["name"]:
            print(contact_book[o-1]["name"])
            print(contact_book[o-1]["info"]["address"])
            print(contact_book[o-1]["info"]["phone"])
            print(contact_book[o-1]["info"]["email"])


if __name__ == "__main__":
    # general setting for Testcases:
    print("Test add_contact:")
    my_contacts = []
    print("Contacts at start: ", my_contacts)

    # Test1 add_contact Information:
    print("\n add_contact 1:")
    name = "Klaus"
    address = "Mainzer Landstr. 24"
    email = "klaus@email.de"
    phone = "0157 035612441"
    add_contact(my_contacts, name, phone, address, email)
    # Output of new contactlist.
    for m in range(len(my_contacts)):
        print(my_contacts[m - 1])
        m += 1

    # Test add_contact 2 Information:
    print("\n add_contact 2:")
    name = "Dieter"
    address = "Muenchender Feldweg 108"
    email = "dieter@email.de"
    phone = "0157 035234617"
    add_contact(my_contacts, name, phone, address, email)
    # Output of new contactlist.
    for m in range(len(my_contacts)):
        print(my_contacts[m - 1])
        m += 1

    # Test add_contact 3 information:
    print("\n add_contact 3:")
    name = "Heinrich"
    address = "Beethoven Str. 29"
    email = "heinrich@email.de"
    phone = "0157 12546261"
    add_contact(my_contacts, name, phone, address, email)
    # output of new contact list.
    for m in range(len(my_contacts)):
        print(my_contacts[m - 1])
        m += 1

    # Test add_contact 4 information:
    print("\n add_contact 4:")
    name = "Le Meridien Frankfurt"
    address = "Wiesenhüttenplats 28-38, 60329 Frankfurt am Main"
    email = "info@lemeridienfrankfurt.com"
    phone = "0069 26970"
    add_contact(my_contacts, name, phone, address, email)
    # output of updated list.
    for m in range(len(my_contacts)):
        print(my_contacts[m - 1])
        m += 1

    # Test add_contact 5 information:
    print("\n add_contact 5:")
    name = "Tanzschule Pelzer"
    address = "Zum Quellenpark 31, 65812 Bad Soden am Taunus"
    email = "info@tanzschule-pelzer.de"
    phone = "06196 28043"
    add_contact(my_contacts, name, phone, address, email)
    # output of updated list.
    for m in range(len(my_contacts)):
        print(my_contacts[m - 1])
        m += 1

    # Test add_contact 6 information:
    print("\n add_contact 6:")
    name = "Schreiner Wattke"
    address = "Keine, Ahnung Wo"
    email = "unknown"
    phone = "06196 21351"
    add_contact(my_contacts, name, phone, address, email)
    # output of updated list
    for m in range(len(my_contacts)):
        print(my_contacts[m - 1])
        m += 1

    # Test for list_contacts between other testcases to check if function
    # always puts out all contacts with changes.
    # Test1 list_contacts:
    print("\nTest list_contacts:")
    print("Test1 list_contact")
    list_contacts(my_contacts)

    # Test1 del_contact:
    print("\n")
    print("Test del_contact:\ndelete 1:")
    del1 = "Tanzschule Pelzer"
    del_contact(my_contacts, del1)
    # output updated list
    for n in range(len(my_contacts)):
        print(my_contacts[n - 1])
        n += 1

    # Test2 del_contact:
    print("\ndelete 2:")
    del2 = "Heinrich"
    del_contact(my_contacts, del2)
    # output updated list
    for n in range(len(my_contacts)):
        print(my_contacts[n - 1])
        n += 1

    # Test2 list_contacts:
    print("\nTest list_contacts:")
    print("Test2 list_contact")
    list_contacts(my_contacts)

    # Test3 del_contact
    print("\ndelete 3:")
    del3 = "Dieter"
    del_contact(my_contacts, del3)
    # output updated list
    for n in range(len(my_contacts)):
        print(my_contacts[n - 1])
        n += 1

    # Test edit_contact 1: Is address editable?
    print("\nTest edit_contact:")
    print("edit_contact 1:")
    name1 = "Le Meridien Frankfurt"
    edit_inf = "address"
    update = "Wiesenhüttenplatz 28, 60329 Frankfurt"
    edit_contact(my_contacts, name1, edit_inf, update)
    # output updated list
    for n in range(len(my_contacts)):
        print(my_contacts[n - 1])
        n += 1

    # Test edit_contact 2: Is phone editable?
    print("edit_contact 2:")
    name1 = "Klaus"
    edit_inf = "phone"
    update = "06196 24444"
    edit_contact(my_contacts, name1, edit_inf, update)
    # output updated list
    for n in range(len(my_contacts)):
        print(my_contacts[n - 1])
        n += 1

    # Test edit_contact 3: Is email editable?
    print("edit_contact 3:")
    name1 = "Le Meridien Frankfurt"
    edit_inf = "email"
    update = "reception@lemeridienfrankfurt.com"
    edit_contact(my_contacts, name1, edit_inf, update)
    # output updated list
    for n in range(len(my_contacts)):
        print(my_contacts[n - 1])
        n += 1

    # Test list_contacts:
    print("\nTest list_contacts:")
    print("Test3 list_contact")
    list_contacts(my_contacts)

    # Three different contacts used to see if different contacts can be
    # excessed.
    # Test show_contact
    print("\nTest show_contact:")
    print("Test1 show_contact:")
    name = "Le Meridien Frankfurt"
    show_contact(my_contacts, name)

    # Test2 show_contact
    print("\nTest 2 show_contact:")
    name2 = "Klaus"
    show_contact(my_contacts, name2)

    # Test3 show_contact
    print("\nTest 3 show_contact:")
    name3 = "Schreiner Wattke"
    show_contact(my_contacts, name3)
