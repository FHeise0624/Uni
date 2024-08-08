__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"
'''This program is a game of matches, each round the players can draw matches,
 whoever draws the last match wins'''

# Definition of game setting.
match_head = ["()", "()", "()", "()", "()", "()", "()",
              "()", "()", "()", "()", "()", "()"]
match_stick = ["||", "||", "||", "||", "||", "||", "||",
               "||", "||", "||", "||", "||", "||"]


# Takes amount of drawn matches per player
# and puts out the remaining on console.
def draw(x, y):
    take = 1
    take_p1 = int(input("Wie viele Streichhölzer \
ziehen Sie Spieler_1? 1, 2, 3? "))
    if take_p1 <= 3:
        while take <= take_p1:
            match_head.remove("()")
            match_stick.remove("||")
            take += 1
        print(match_head)
        print(match_stick)
        print(match_stick)
        print(match_stick)
    else:
        print("Sie dürfen maximal 3 Streichhölzer nehmen! Zur strafe \
        wird Ihr Zug übersprungen.")

    take_p2 = int(input("Wie viele Streichhölzer \
ziehen Sie Spieler_2? 1, 2, 3? "))
    take = 1
    if take_p2 <= 3:
        while take <= take_p2:
            match_head.remove("()")
            match_stick.remove("||")
            take += 1
        print(match_head)
        print(match_stick)
        print(match_stick)
        print(match_stick)
    else:
        print("Sie dürfen maximal 3 Streichhölzer nehmen! Zur strafe \
        wird Ihr Zug übersprungen.")

# output of the game.
print("Herzlich willkommen Spieler, in diesem Spiel ziehen Sie abwechselnd \
1-3 Streichhölzer, der Spieler der das letzte Streichholz zieht \
hat gewonnen.")
print(match_head)
print(match_stick)
print(match_stick)
print(match_stick)

# Game rounds loop.
while len(match_stick) != 0:
    # Output of remaining matches or winner.
    draw(match_stick, match_head)
    if len(match_stick) == 0:
        print("Herzlichen Glückwunsch Spieler_1, Sie haben gewonnen!")
        break

    draw(match_stick, match_head)
    if len(match_stick) == 0:
        print("Herzlichen Glückwunsch Spieler_2, Sie haben gewonnen!")
        break
