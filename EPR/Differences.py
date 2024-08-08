"""
This Program calculates the difference between a list element and the
following element in a List. For this the difference function is one time
defined iterative and one time recursive.
"""
__author__ = "977844, Tams"
__credits__ = "Thanks to " \
              "https://www.python-kurs.eu/python3_rekursive_funktionen.php" \
              "for their input on recursion"
__email__ = "s5668531@stud.uni-frankfurt.de"


def differences(values):
    """
    This function calculates the difference between a list element and its
    following.
    :param values: list of integer elements
    :return: list of values stating the differences between list elements.
    """
    result = []
    # calculates the difference for each element using a for-loop.
    for i in range(len(values) - 1):
        result.append(values[i + 1] - values[i])
    return result


def differences_rec(values):
    """
    This function calculates the difference between each list element and
    its following and returns the results in a list.
    :param values: list of elements of which the differences should be
    calculated.
    :return: results = list of elements that show the results for the
    differences.
    """
    results = []
    # condition when to stop recursion
    if len(values) == 2:
        results.append(values[1] - values[0])
        return results
    # condition that triggers calculation and recursion to cover every list
    # element.
    else:
        results.insert(0, values[1] - values[0])
        return results + differences_rec(values[1:])


# definition of three testcases for comparison of iterative and recursive
# function for difference calculation.
if __name__ == "__main__":
    trial_1 = [1, 2, 3, 4, 5]
    trial_2 = [3, 7, 2, 9, 3, 5, 24, 18]
    trial_3 = [15, 145, 363, -12, 342, 12]
    print("Testcase 1\nIterative:")
    print(differences(trial_1))
    print("Recursive")
    print(differences_rec(trial_1))
    print()
    print("Testcase 2\nIterative: ")
    print(differences(trial_2))
    print("Recursive:")
    print(differences_rec(trial_2))
    print()
    print("Testcase 3\nIterative: ")
    print(differences(trial_3))
    print("Recursive:")
    print(differences_rec(trial_3))
