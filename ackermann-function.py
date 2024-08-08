"""
"""
__author__ = "977844, Tams"
__credits__ = '''If you would like to thank somebody
              i.e. an other student for her code or leave it out.'''
__email__ = "s5668531@stud.uni-frankfurt.de"


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
