"""
This Function creates an n x m-Matrix consisting of random integers between
-2000 and 4000
"""
__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"

import random


def create_matrix(n=2, m=2):
    """
    This function takes in two parameters m, n for the dimension of a matrix
    and creates an m x n-Matrix.
    creates a matrix
    :param n: n = int (length of lines)
    :param m: m = int (amount of colums)
    :return: matrix in dimension of(m x n)
    """
    # creating a dictionary and necessary variables for matrix calculation
    matrix = dict()
    line = []
    i = 0
    j = 0
    k = 0

    # check for false input
    if n.isdigit():
        n = int(n)
        if int(n) >= 2:
            if m.isdigit():
                m = int(m)
                if int(m) >= 2:

                    # generating random numbers and storing in a n x m
                    # dimensional dictionary
                    while j in range(0, m):
                        for i in range(0, n):
                            line.append(random.randint(-2000, 4001))
                            i += 1
                        matrix[j] = line
                        line = []
                        j += 1

                    # return matrix
                    return matrix

                # generating error-messages for faulty input
                else:
                    print("Please only enter positive integers above 1 for the"
                          " dimensions of the matrix!")
            else:
                print("Please only enter positive integers above 1 for the "
                      "dimensions of the matrix!")
        else:
            print("Please only enter positive integers above 1 for the "
                  "dimensions of the matrix!")
    else:
        print(print("Please only enter positive integers above 1 for the "
                    "dimensions of the matrix!"))


if __name__ == "__main__":
    # User input  for matrix dimensions
    len_line = input("Please enter a positive integer above 2: ")
    len_colum = input("Please  enter a positive integer above 2: ")

    # storing matrix for excess outside of function
    matrix = create_matrix(len_line, len_colum)

    # output of matrix on console
    colums = int(len_colum)
    i = 0
    for i in range(0, colums):
        print(tuple(matrix[i]))
