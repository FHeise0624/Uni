"""
This Program checks if an input-string of parentheses is balanced or not.
"""
__author__ = "977844, Tams"
__email__ = "s5668531@stud.uni-frankfurt.de"


def balance_check(x):
    stack = []
    answer = 1
    parentheses = {"(":")", "[":"]", "{":"}"}
    k = 0
    if len(x) % 2 == 0:
        for j in x:
            if j in parentheses.keys():
                stack.insert(0, j)
            elif j in parentheses.values():
                if j == parentheses.get(stack[0]):
                    stack.pop(0)
        if len(stack) == 0:
            answer = 1
        else:
            answer = 0
    else:
        answer = 0
    return answer


if __name__ == "__main__":
    # Testcase 1:
    test_1 = "()"
    print(balance_check(test_1))

    # Testcase 2:
    test_2 = "[[]]("
    print(balance_check(test_2))
    
    # Testcase 3:
    test_3 = "(()("
    print(balance_check(test_3))
