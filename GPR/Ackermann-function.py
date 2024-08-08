"""
"""
__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"


def ack(n, m):
    """
    This Function takes in two int Values and solves  the ackermann function
    for these values
    :param n: int
    :param m: int
    :return:
    """
    if n == 0:
        return m + 1
    elif m == 0 & n != 0:
        return ack(n - 1, 1)
    else:
        return ack(n - 1, ack(n, m - 1))


if __name__ == "__main__":
    n = 0
    m = 1
    print(ack(n, m))
