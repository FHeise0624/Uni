__author__ = "977844, Tams"
__email__ = "s5668531@stud.uni-frankfurt.de"
"""
this module provides several useful function that one constantly
needs.
"""


def decimal_to_binary(x):
    """
    This function converts a positive decimal number into a binary number
    arguments: type(X) = int
    """
    b = []
    # input == positive int
    x = int(x)
    # Calculation of binary number by appending rest of mod calculation to
    # list and continuing calculation of whole number of calculation until
    # reaching 0
    while x > 0:
        b.insert(0, x % 2)
        print(x, "modulo 2 = ", x % 2)
        print("Rest = ", x // 2)
        x = x // 2
    # returning the binary number
    return b


if __name__ == '__main__':
    # Testcase 1
    dec_num = 13
    print(decimal_to_binary(dec_num))
    # Testcase 2
    dec_num_2 = 2789
    print(decimal_to_binary(dec_num_2))
    # Testcase 3
    dec_num_3 = 5
    print(decimal_to_binary(dec_num_3))


def exam_countdown(t):
    """
    This Function takes in a Date and Time, calculates the remaining Time
    until the EPI Exam and has a Counter showing Days, Hours, Minutes
    as an output.
    This function automatically imports the time module.
    :param t: a tuple(year = , month = , day = , hours = , minutes = )
    :return: output of a timer on the console
    """
    import time
    # information on time module originates from:
    # https: // www.youtube.com / watch?v = Qj3GlL5ckQA

    # condition to check if the function received input.
    if len(t) != 0:
        # if input was received a time tuple will be created and converted
        # into seconds since epoch for the input and exam date and time
        t = tuple(int(item)for item in t.split())
        t = t + (0, 0, 0, 0)
        exam = (2023, 2, 16, 10, 0, 0, 0, 0, 0)
        time_input = time.mktime(t)
        time_exam = time.mktime(exam)
        # calculation of remaining time until the exam
        time_till = time_exam - time_input
        i = time_till
        # conversion of time in seconds into days, hours and minutes +
        # output on console
        for i in range(int(time_till), 0, -1):
            min = int(i / 60) % 60
            hou = int(i / 3600) % 24
            day = int(i / 86400)
            print(f"Noch {day:02} Tage, {hou:02} Stunden,"
                  f"{min:02} Minuten bis zur EPI Klausur")
            time.sleep(60)
    # if no input is received system time is used instead, rest similar to
    # above
    else:
        exam = (2023, 2, 16, 10, 0, 0, 0, 0, 0)
        time_exam = time.mktime(exam)
        time_till = time_exam - time.time()
        i = time_till
        for i in range(int(time_till), 0, -1):
            min = int(i / 60) % 60
            hou = int(i / 3600) % 24
            day = int(i / 86400)
            print(f"Noch {day:02} Tage, {hou:02} Stunden,"
                  f"{min:02} Minuten bis zur EPI Klausur")
            time.sleep(60)


if __name__ == '__main__':
    # Test Case 1:
    test_time_1 = ()
    print(exam_countdown(test_time_1))

    # Test case 2:
    test_time_2 = (2022, 12, 14, 16, 35)
    print(exam_countdown(test_time_2))

    # Test case 3:
    test_time_3 = (2023, 2, 15, 10, 59)
    print(exam_countdown(test_time_3))


def open_course_page(x):
    """
    This function opens the website of one of the following Courses,
    according to the input of the user.
    :param x: GPR, LinADi, DisMod, EPR
    """
    # the information about the webbrowser module originates form:
    # https://docs.python.org/3/library/webbrowser.html#module-webbrowser
    # execution of the open function regarding the input of the user.
    import webbrowser
    # execution for input EPR
    if x == "EPR":
        url = "https://moodle.studiumdigitale.uni-frankfurt.de/moodle/course/view.php?id=3292"
        webbrowser.open(url, new=0, autoraise=True)
    # execution for input LinADi
    elif x == "LinADi":
        webbrowser.open("https://www.uni-frankfurt.de/115711235", new=0,
                        autoraise=True)
    # execution for input DisMod
    elif x == "DisMod":
        webbrowser.open("https://ae.cs.uni-frankfurt.de/dismod22",
                        new=0, autoraise=True)
    # execution for input GPR
    elif x == "GPR":
        url = "https://moodle.studiumdigitale.uni-frankfurt.de/moodle/course/view.php?id=3294"
        webbrowser.open(url, new=0, autoraise=True)


if __name__ == '__main__':
    # Test case 1:
    course_1 = "EPR"
    open_course_page(course_1)

    # Test case 2:
    course_2 = "LinADi"
    open_course_page(course_2)

    # Test case 3:
    course_3 = "GPR"
    open_course_page(course_3)


def passwort_gen(length):
    """
    This Program generates a random password according to the required length.
    :param length: int(input())
    :return: str(password)
    """
    # the information about the random module originates from:
    # https://docs.python.org/3/library/random.html#module-random
    import random
    pw = list()
    i = 0
    # the range for the ascii conversion originates from:
    # https://asci-tables.com
    # loop to generate random signs for the password regarding the input length
    for i in range(length):
        # frame for ascii signs for small and capital letters and numbers 0 - 9
        # appending each character to a list that holds the characters for
        # the password
        a = random.randint(48, 123)
        if a in range(48, 58):
            a = chr(a)
            pw.append(a)
        elif a in range(65, 91):
            a = chr(a)
            pw.append(a)
        elif a in range(97, 123):
            a = chr(a)
            pw.append(a)
# the join function originates from
# https://www.delftstack.com/de/howto/python/how-to-convert-a-list-to-string/
    # joining the single element of the list to a single string  & return of
    # generated password
    pw = (''.join(pw))
    return pw


if __name__ == '__main__':
    # Test case 1:
    length_1 = 8
    print(passwort_gen(length_1))

    # Test case 2:
    length_2 = 5
    print(passwort_gen(length_2))

    # Test case 3:
    length_3 = 20
    print(passwort_gen(length_3))
