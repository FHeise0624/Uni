"""
This Program calculates the center point of a List of Points in a
three-dimensional space.
"""
__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"


def middle(points):
    """
    This Function takes a list of three-dimensional tuples and calculates
    the middle point.
    :param points: list of three-dimensional tuples .
    :return: middle: tuple that represents the middle point.
    """
    i = 0
    while i < len(points) - 1:
        middle_point = ((points[i][0] + points[i+1][0]) / len(points),
                        (points[i][1] + points[i+1][1]) / len(points),
                        (points[i][2] + points[i+1][2]) / len(points))
        i += 1
    return middle_point


if __name__ == "__main__":
    your_points = [(12, 5, 7), (5, 13, 15)]
    print(middle(your_points))
    print()
    trial = [(12, 5, 7), (5, 13, 15), (4, 75, 8)]
    print(middle(trial))
