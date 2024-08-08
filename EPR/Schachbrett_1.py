__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"
"""This programm defines a function which creates an 8x8 Chessboard, 
with collums A-H and rows 1-8."""

# definition of function & necessary variables & lists.
def schachbrett():
    x = 0
    row = 1
    colum = ["A", "B", "C", "D", "E", "F", "G", "H"]
    board = []

 #loop which creates the chessboard and output on console.
    for x in colum:
        y = ["A" + str(row), "B" + str(row), "C" + str(row), "D" + str(row),
        "E" + str(row), "F" + str(row), "G" + str(row), "H" + str(row)]
        board.append(y)
        row += 1
        print(board[row - 2])

# Testfunction for the programm
if __name__ == '__main__':
    schachbrett()