"""
"""
__author__ = "977844, Tams"
__credits__ = '''If you would like to thank somebody
              i.e. an other student for her code or leave it out.'''
__email__ = "s5668531@stud.uni-frankfurt.de"


def palindrome(word):
    if len(word) <= 1:
        return True
    if word[0] != word[-1]:
        return False
    return palindrome(word[1:-1])


def palindrome_iter(word):
    i = 0
    if len(word) == 1:
        return True
    for i in range(len(word) - 1):
        if word[i] == word[-i]:
            i += 1
            return True
        else:
            return False


if __name__ == "__main__":
    trial1 = "Hallo"
    trial2 = "BOB"
    trial3 = "ABCDEDCBA"
    print(palindrome(trial1))
    print(palindrome_iter(trial1))
    print()
    print(palindrome(trial2))
    print(palindrome_iter(trial2))
    print()
    print(palindrome(trial3))
    print(palindrome_iter(trial3))
