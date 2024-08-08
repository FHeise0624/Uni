"""
"""
__author__ = "977844, Tams"
__credits__ = '''If you would like to thank somebody
              i.e. an other student for her code or leave it out.'''
__email__ = "s5668531@stud.uni-frankfurt.de"

a1 = 1
a2 = 1
a3 = 1
a4 = 0
i = int(input("Die wievielete Zahl der Zahlenfolge möchten Sie berechnen? "))
n = 1
if i <= 3:
    print("Die ", i, "-te Zahl der Zahlenfolge lautet: 1")
else:
    for n in range(3, i):
        a4 = a3 + a1
        a1 = a2
        a2 = a3
        a3 = a4
        n += 1
print(a4)
