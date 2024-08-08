"""
This Program creates a random n-dimensional matrix and searches for the best
way to get from the start to the end point in that matrix, having the cost
and best way as an output.
"""
__author__ = "977844, Tams"
__email__ = "s5668531@stud.uni-frankfurt.de"
import random


def built_matrix(n=2):
    """
    This function creates an n-dimensional matrix
    :param n: int that states dimension of matrix
    :return: matrix = two-dimensional list
    """
    # setting variables and lists for creating a matrix
    matrix = []
    line = []
    m = n
    # loops that fill the lines and columns of the n-dimensional matrix
    for i in range(n):
        for j in range(m):
            line.append(random.randint(0, 9))
            j += 1
        matrix.append(line)
        line = []
        i += 1

    # output of matrix with each column under each other
    for k in range(n):
        print(matrix[k])
        k += 1
    # return of matrix
    return matrix


def best_way_trough(matrix, start_i=0, start_j=0):
    """
    This function takes a matrix and starting point as an input and searches
    for the cheapest way through the matrix.
    :param matrix: 2-dimensional list.
    :param start_i: fist index of the starting-point.
    :param start_j: second index of the starting point.
    :return: the best way through the matrix and total cost.
    """
    # setting variables and lists for calculation.
    way = []
    i = start_i
    j = start_j
    # end condition for recursion.
    if i == len(matrix):
        return
    # conditions to search for the best way through matrix
    elif matrix[i][j + 1] < matrix[i + 1][j]:
        way.append(matrix[i][j + 1])
        print(way)
        return way, best_way_trough(matrix), start_i, start_j + 1
    elif matrix[i + 1][j] < matrix[i][j + 1]:
        way.append(matrix[i + 1][j])
        print(way)
        return way, best_way_trough(matrix), start_i + 1, start_j
    print(i)


# Testcases.
if __name__ == "__main__":
    print("Testcase 1:")
    dimension = 2
    matrix_1 = built_matrix(dimension)
    print()
    best_way_trough(matrix_1)
    print()

    print("Testcase 2:")
    dimension_2 = 3
    matrix_2 = built_matrix(dimension_2)
    print()
    best_way_trough(matrix_2)
    print()

    print("Testcase 3:")
    dimension_3 = 4
    matrix_3 = built_matrix(dimension_3)
    print()
    best_way_trough(matrix_3)
