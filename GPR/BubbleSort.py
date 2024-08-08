"""
This Program takes in a List, sorts its content and puts out the sorted list.
"""
__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"
__credits__ = '''Thanks for the information on the Bubble Sort algorithm:
 https://studyflix.de/informatik/bubblesort
-1325'''



def bubble_sort(tobesorted):
    """
    This Function takes in an unsorted list of integers, sorts it and prints
    the sorted list and the amount of needed comparisons to sort the list.
    :param tobesorted: list of integers
    :return: print(sortedlist) & print(amount of needed comparisons)
    """
    # loop
    i = 0
    count2 = 0
    while i in range(0, len(tobesorted)):
        i += 1
        checked_list = tobesorted
        j = 0
        countloop = 0
        # loop that compares every element of the list to the following.
        while j < (len(checked_list) - 1):
            # exchange of elements if current is smaller than next.
            if checked_list[j] > checked_list[j + 1]:
                x = checked_list[j]
                checked_list[j] = checked_list[j + 1]
                checked_list[j + 1] = x
                sorted_list = checked_list
                print(sorted_list)
                print()
                count2 += 1
            # copies list if element is smaller than the next one.
            elif checked_list[j] < checked_list[j + 1]:
                sorted_list = checked_list
                j += 1
            # if elements are same element gets skipped.
            else:
                j += 1
            countloop += 1
    # Output of the sorted list and the amount of needed comparisons.
    print("The sorted list ist following: ")
    print(sorted_list)
    if count2 > 0:
        print("The Program needed: ", countloop * count2,
              "comparisons to sort your list.")
    else:
        print("The Program needed: ", countloop, "to sort your "
              "list.")


if __name__ == "__main__":
    # Check if there will bie an input or testcases should be run.
    answer = input("Would you like to have look at the Testcases or enter "
                   "your own List?\nEnter 'Test' or 'Own': ")

    if answer == "Own":
        # User input List of integers
        user_list = list(input("Please enter a List of integers, like this"
                               "'1231445': "))
        a = 0
        # checks if every element is a number.
        while a in range(0, (len(user_list) - 1)):
            if user_list[a].isdigit():
                a += 1
                checked = user_list
            else:
                print("Please only enter integers for the list.")
        bubble_sort(checked)

    # if user does not want to enter an own list, program runs testcases.
    elif answer == "Test":
        # Testcase 1.
        test1 = [1, 3, 5, 4, 1, 4, 3, 3, 3]
        bubble_sort(test1)
        # Testcase 2.
        test2 = [1, 2, 3, 4, 5]
        bubble_sort(test2)
        # Testcase 3
        test3 = [9, 8, 7, 6, 5, 4, 3, 2]
        bubble_sort(test3)
    # if input is different from the allowed ones, output of error.
    else:
        print("Input not supported!")
