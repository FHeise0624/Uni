__author__ = "977844, Tams"
__email__ = "s5668531@stud.uni-frankfurt.de"
"""Program that takes two inputs on the Queens position(row and colum)
on a chessboard and has a chessboard with the according position of the
Queen as an output."""


def schachbrett_koenigin(colum_q, row_q):
    # Definition of lists and variables.
    row = 1
    colum = ["A", "B", "C", "D", "E", "F", "G", "H"]
    board = []
    i = 0

    # Definition of index of y depending on the input.
    match colum_q:
        case "A":
            i = 0
        case "B":
            i = 1
        case "C":
            i = 2
        case "D":
            i = 3
        case "E":
            i = 4
        case "F":
            i = 5
        case "G":
            i = 6
        case "H":
            i = 7

    # Construction of the chessboard and positioning of the Queen,
    # with a loop.
    for x in colum:
        y = ["A" + str(row), "B" + str(row), "C" + str(row), "D" + str(row),
             "E" + str(row), "F" + str(row), "G" + str(row), "H" + str(row)]
        if str(colum_q) + str(row_q) == y[i]:
            y[i] = "QQ"
        board.append(y)
        row += 1

        print(board[row - 2])


# Testfunction for the Program.
if __name__ == '__main__':
    colum_q = input("Please enter colum of Queen: ")
    row_q = input("Please enter Row of Queen: ")
    schachbrett_koenigin(colum_q, row_q)

# Test Case_1:
# Input:
# Please enter colum of Queen:
# Please enter Row of Queen:

# Output:
# ['A1', 'B1', 'C1', 'D1', 'E1', 'F1', 'G1', 'H1']
# ['A2', 'B2', 'C2', 'D2', 'E2', 'F2', 'G2', 'H2']
# ['A3', 'B3', 'C3', 'D3', 'E3', 'F3', 'G3', 'H3']
# ['A4', 'B4', 'C4', 'D4', 'E4', 'F4', 'G4', 'H4']
# ['A5', 'B5', 'C5', 'D5', 'E5', 'F5', 'G5', 'H5']
# ['A6', 'B6', 'C6', 'D6', 'E6', 'F6', 'G6', 'H6']
# ['A7', 'B7', 'C7', 'D7', 'E7', 'F7', 'G7', 'H7']
# ['A8', 'B8', 'C8', 'D8', 'E8', 'F8', 'G8', 'H8']

# Test Case_2:
# Input:
# Please enter colum of Queen: C
# Please enter Row of Queen: 5

# Output:
# ['A1', 'B1', 'C1', 'D1', 'E1', 'F1', 'G1', 'H1']
# ['A2', 'B2', 'C2', 'D2', 'E2', 'F2', 'G2', 'H2']
# ['A3', 'B3', 'C3', 'D3', 'E3', 'F3', 'G3', 'H3']
# ['A4', 'B4', 'C4', 'D4', 'E4', 'F4', 'G4', 'H4']
# ['A5', 'B5', 'QQ', 'D5', 'E5', 'F5', 'G5', 'H5']
# ['A6', 'B6', 'C6', 'D6', 'E6', 'F6', 'G6', 'H6']
# ['A7', 'B7', 'C7', 'D7', 'E7', 'F7', 'G7', 'H7']
# ['A8', 'B8', 'C8', 'D8', 'E8', 'F8', 'G8', 'H8']


# Test Case_3:
# Input:
# Please enter colum of Queen: H
# Please enter Row of Queen: 8

# Output:
# ['A1', 'B1', 'C1', 'D1', 'E1', 'F1', 'G1', 'H1']
# ['A2', 'B2', 'C2', 'D2', 'E2', 'F2', 'G2', 'H2']
# ['A3', 'B3', 'C3', 'D3', 'E3', 'F3', 'G3', 'H3']
# ['A4', 'B4', 'C4', 'D4', 'E4', 'F4', 'G4', 'H4']
# ['A5', 'B5', 'C5', 'D5', 'E5', 'F5', 'G5', 'H5']
# ['A6', 'B6', 'C6', 'D6', 'E6', 'F6', 'G6', 'H6']
# ['A7', 'B7', 'C7', 'D7', 'E7', 'F7', 'G7', 'H7']
# ['A8', 'B8', 'C8', 'D8', 'E8', 'F8', 'G8', 'QQ']

