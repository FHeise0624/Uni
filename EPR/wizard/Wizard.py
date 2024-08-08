"""
This Programm implements the Game wizard.
"""
__author__ = "Felix Heise"
__email__ = "fheise.fh@gmail.com"

# import of needed data
import random
import cards_functions


# defining clear screen
def clear_screen():
    """
    This function clears the console.
    :return: output of 50 blanc lines to clear screen.
    """
    clear = "\n" * 50
    print(clear)


x = "Nein"
while x != "Ja":
    # output of game rules
    print("Lieber Spieler, herzlich willkommen zu wizard, einem Spiel für "
          "2 bis 5 Spieler.")
    print()
    print("Regeln: \n"
          "1. Pro Runde erhält jeder Spieler genauso viele Karten, wie die\n "
          "aktuelle Runde. \n"
          "2. In jeder Runde wird um Stiche gespielt. Die Anzahl der Stiche\n "
          "  entspricht der Runde, welche gespielt wird.\n"
          "3. Pro Stich darf jeder Spieler genau 1 Karte legen, der Spieler,"
          "\n "
          " der die Karte mit dem höchsten Wert legt gewinnt.\n"
          "4. Zu Beginn des Spielst wird zufällig der 1. Spieler festgelegt.\n"
          " In dieser Runde darf dieser Spieler die erste Karte legen,\n"
          " in  den darauf folgenden Stichen, beginnt jeweils der "
          "folgende\n "
          " Spieler. In der nächsten Runde beginnt dann Spieler 2 und so \n"
          " weiter in den darauf folgenden Runden.\n"
          "5. Die Anzahl der Runden hängt von der Anzahl der Mitspieler ab.\n"
          "6. Die Karten werden nach jeder Runde neu gemischt.")
    print()
    print("Auswertung:\n"
          "1. In jeder Runde wird zufällig eine Trumpf-Farbe ausgewählt.\n"
          " Karten dieser Farbe sind höherwertig als Karten anderer Farben,\n "
          " unabhängig vom Wert.\n"
          "2. Kartenfarben haben absteigend folgende Wertigkeit: Kreuz, Pik,"
          "\n "
          " Karo, Herz.\n"
          "3. Karten einer Farbe haben absteigend folgende Wertigkeit: Ass,\n "
          " König, Dame, Bube, 10, 9, 8, 7, 6, 5, 4, 3, 2.")
    print()
    x = input("Möchten Sie mit dem Spiel beginnen?\n"
              "Ja oder Nein?: ")
clear_screen()

# establishing player amount.
players = int(input("Mit wie vielen Spielern möchten Sie insgesamt spielen?\n"
                    "Wählen Sie zwischen 1 - 5 Spielern: "))
clear_screen()
# establishing player bots, according to total player amount and player
# choice of bot amount.
bot_choice = input("Möchten Sie mit Computergegner Spielen? Ja oder Nein?: ")
clear_screen()
if bot_choice == "Nein":
    bots = 0
    if players == 2:
        player_1 = input(
            "Bitte geben Sie den Namen des ersten Spielers ein.: ")
        player_2 = input(
            "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
        player_order = [player_1, player_2]
    elif players == 3:
        player_1 = input(
            "Bitte geben Sie den Namen des ersten Spielers ein.: ")
        player_2 = input(
            "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
        player_3 = input(
            "Bitte geben Sie den Namen des dritten Spielers ein.: ")
        player_order = [player_1, player_2, player_3]
    elif players == 4:
        player_1 = input(
            "Bitte geben Sie den Namen des ersten Spielers ein.: ")
        player_2 = input(
            "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
        player_3 = input(
            "Bitte geben Sie den Namen des dritten Spielers ein.: ")
        player_4 = input(
            "Bitte geben Sie den Namen des vierten Spielers ein.: ")
        player_order = [player_1, player_2, player_3, player_4]
    elif players == 5:
        player_1 = input(
            "Bitte geben Sie den Namen des ersten Spielers ein.: ")
        player_2 = input(
            "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
        player_3 = input(
            "Bitte geben Sie den Namen des dritten Spielers ein.: ")
        player_4 = input(
            "Bitte geben Sie den Namen des vierten Spielers ein.: ")
        player_5 = input(
            "Bitte geben Sie den Namen des fünften Spielers ein.: ")
        player_order = [player_1, player_2, player_3, player_4, player_5]
else:
    if players == 2:
        bots = 1
        player_1 = input(
            "Bitte geben Sie den Namen des ersten Spielers ein.: ")
        player_2 = ("Bot_Felix", "Bot")
        player_order = [player_1, player_2]
    elif players == 3:
        bots = int(input("Möchten Sie mit 1 oder 2 Computergegner spielen?: "))
        if bots == 1:
            player_1 = input(
                "Bitte geben Sie den Namen des ersten Spielers ein.: ")
            player_2 = input(
                "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
            player_3 = ("Bot_Felix", "Bot")
            player_order = [player_1, player_2, player_3]
        else:
            player_1 = input(
                "Bitte geben Sie den Namen des ersten Spielers ein.: ")
            player_2 = ("Bot_Emilie", "Bot")
            player_3 = ("Bot_Felix", "Bot")
            player_order = [player_1, player_2, player_3]
    elif players == 4:
        bots = int(input("Möchten Sie mit 1, 2 oder 3 Computergegner "
                         "spielen?: "))
        if bots == 1:
            player_1 = input(
                "Bitte geben Sie den Namen des ersten Spielers ein.: ")
            player_2 = input(
                "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
            player_3 = input(
                "Bitte geben Sie den Namen des dritten Spielers ein.: ")
            player_4 = ("Bot_Felix", "Bot")
            player_order = [player_1, player_2, player_3, player_4]
        elif bots == 2:
            player_1 = input(
                "Bitte geben Sie den Namen des ersten Spielers ein.: ")
            player_2 = input(
                "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
            player_3 = ("Bot_Emilie", "Bot")
            player_4 = ("Bot_Felix", "Bot")
            player_order = [player_1, player_2, player_3, player_4]
        else:
            player_1 = input(
                "Bitte geben Sie den Namen des ersten Spielers ein.: ")
            player_2 = ("Bot_Tom", "Bot")
            player_3 = ("Bot_Emilie", "Bot")
            player_4 = ("Bot_Felix", "Bot")
            player_order = [player_1, player_2, player_3, player_4]
    elif players == 5:
        bots = int(input("Möchten Sie mit 1, 2, 3 oder 4 Computergegner "
                         "spielen?: "))
        if bots == 1:
            player_1 = input(
                "Bitte geben Sie den Namen des ersten Spielers ein.: ")
            player_2 = input(
                "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
            player_3 = input(
                "Bitte geben Sie den Namen des dritten Spielers ein.: ")
            player_4 = input(
                "Bitte geben Sie den Namen des vierten Spielers ein.: ")
            player_5 = ("Bot_Felix", "Bot")
            player_order = [player_1, player_2, player_3, player_4, player_5]
        elif bots == 2:
            player_1 = input(
                "Bitte geben Sie den Namen des ersten Spielers ein.: ")
            player_2 = input(
                "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
            player_3 = input(
                "Bitte geben Sie den Namen des dritten Spielers ein.: ")
            player_4 = ("Bot_Emilie", "Bot")
            player_5 = ("Bot_Felix", "Bot")
            player_order = [player_1, player_2, player_3, player_4, player_5]
        elif bots == 3:
            player_1 = input(
                "Bitte geben Sie den Namen des ersten Spielers ein.: ")
            player_2 = input(
                "Bitte geben Sie den Namen des zweiten Spielers ein.: ")
            player_3 = ("Bot_Tom", "Bot")
            player_4 = ("Bot_Emilie", "Bot")
            player_5 = ("Bot_Felix", "Bot")
            player_order = [player_1, player_2, player_3, player_4, player_5]
        else:
            player_1 = input(
                "Bitte geben Sie den Namen des ersten Spielers ein.: ")
            player_2 = ("Bot_Clara", "Bot")
            player_3 = ("Bot_Tom", "Bot")
            player_4 = ("Bot_Emilie", "Bot")
            player_5 = ("Bot_Felix", "Bot")
            player_order = [player_1, player_2, player_3, player_4, player_5]
    if bots >= players:
        print("Es können maximal so viele Computerspieler aktiviert "
              "werden, wie zu Ihnen zusätzliche Spieler am Spiel "
              "teilnehmen.")
clear_screen()

# creating card deck
cards = cards_functions.create_card()
rounds = len(cards) // players
i = 0
round_no = 0
colours = ["Kreuz", "Pik", "Karo", "Herz"]

# loop representing game rounds.
for i in range(0, rounds - 1):
    # establishing first player for round
    random.shuffle(player_order)
    if players == 2:
        p1 = player_order[0]
        p2 = player_order[1]
    elif players == 3:
        p1 = player_order[0]
        p2 = player_order[1]
        p3 = player_order[2]
    elif players == 4:
        p1 = player_order[0]
        p2 = player_order[1]
        p3 = player_order[2]
        p4 = player_order[3]
    elif players == 5:
        p1 = player_order[0]
        p2 = player_order[1]
        p3 = player_order[2]
        p4 = player_order[3]
        p5 = player_order[4]
    print("Spieler: ", player_order[0], "beginnt diese Runde")
    print()
    print("Runde ", i + 1)

    # setting basic stats for sting.
    j = 0
    trump = random.choice(colours)
    round_no += 1
    hands = cards_functions.deal_cards(cards, round_no, players)
    stings = len(hands[0])
    print(hands)

    # loop representing stings of each round.
    while j in range(0, stings):
        # setting empty stack of played cards each sting.
        played = []
        print("Stich ", j + 1)
        print(j)
        print(len(hands[0]))
        # playing cards of each player depending on player amount.
        if players == 2:
            # if player is a bot random choice of a card on bots hand.
            if p1[1] == "Bot":
                print(p1, "wählt seine Karte.")
                x = random.randint(0, len(hands[0]))
                played.append(hands[0][x])
                hands[0].pop(x)
            else:
                # if player is no bot choice of card to play.
                print(p1, "welche Karte", hands[0], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[0][x])
                hands[0].pop(x)
                clear_screen()
            if p2[1] == "Bot":
                print(p2, "wählt seine Karte.")
                x = random.randint(0, len(hands[1]))
                played.append(hands[1][x])
                hands[1].pop(x)
            else:
                print(p2, "welche Karte", hands[1], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[1][x])
                hands[1].pop(x)
                clear_screen()
        elif players == 3:
            if p1[1] == "Bot":
                print(p1, "wählt seine Karte.")
                x = random.randint(0, len(hands[0]))
                played.append(hands[0][x])
                hands[0].pop(x)
            else:
                print(p1, "welche Karte", hands[0], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[0][x])
                hands[0].pop(x)
                clear_screen()
            if p2[1] == "Bot":
                print(p2, "wählt seine Karte.")
                x = random.randint(0, len(hands[1]))
                played.append(hands[1][x])
                hands[1].pop(x)
            else:
                print(p2, "welche Karte", hands[1], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[1][x])
                hands[1].pop(x)
                clear_screen()
            if p3[1] == "Bot":
                print(p3, "wählt seine Karte.")
                x = random.randint(0, len(hands[2]))
                played.append(hands[2][x])
                hands[2].pop(x)
            else:
                print(p3, "welche Karte", hands[2], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[2][x])
                hands[2].pop(x)
                clear_screen()
        elif players == 4:
            if p1[1] == "Bot":
                print(p1, "wählt seine Karte.")
                x = random.randint(0, len(hands[0]))
                played.append(hands[0][x])
                hands[0].pop(x)
            else:
                print(p1, "welche Karte", hands[0], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[0][x])
                hands[0].pop(x)
                clear_screen()
            if p2[1] == "Bot":
                print(p2, "wählt seine Karte.")
                x = random.randint(0, len(hands[1]))
                played.append(hands[1][x])
                hands[1].pop(x)
            else:
                print(p2, "welche Karte", hands[1], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[1][x])
                hands[1].pop(x)
                clear_screen()
            if p3[1] == "Bot":
                print(p3, "wählt seine Karte.")
                x = random.randint(0, len(hands[2]))
                played.append(hands[2][x])
                hands[2].pop(x)
            else:
                print(p3, "welche Karte", hands[2], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[2][x])
                hands[2].pop(x)
                clear_screen()
            if p4[1] == "Bot":
                print(p4, "wählt seine Karte.")
                x = random.randint(0, len(hands[3]))
                played.append(hands[3][x])
                hands[3].pop(x)
            else:
                print(p4, "welche Karte", hands[3], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[3][x])
                hands[3].pop(x)
                clear_screen()
        else:
            if p1[1] == "Bot":
                print(p1, "wählt seine Karte.")
                x = random.randint(0, len(hands[0]))
                played.append(hands[0][x])
                hands[0].pop(x)
            else:
                print(p1, "welche Karte", hands[0], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[0][x])
                hands[0].pop(x)
                clear_screen()
            if p2[1] == "Bot":
                print(p2, "wählt seine Karte.")
                x = random.randint(0, len(hands[1]))
                played.append(hands[1][x])
                hands[1].pop(x)
            else:
                print(p2, "welche Karten", hands[1], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[1][x])
                hands[1].pop(x)
                clear_screen()
            if p3[1] == "Bot":
                print(p3, "wählt seine Karte.")
                x = random.randint(0, len(hands[2]))
                played.append(hands[2][x])
                hands[2].pop(x)
            else:
                print(p3, "welche Karte", hands[2], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[2][x])
                hands[2].pop(x)
                clear_screen()
            if p4[1] == "Bot":
                print(p4, "wählt seine Karte.")
                x = random.randint(0, len(hands[3]))
                played.append(hands[3][x])
                hands[3].pop(x)
            else:
                print(p4, "welche Karte", hands[3], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[3][x])
                hands[3].pop(x)
                clear_screen()
            if p5[1] == "Bot":
                print(p5, "wählt seine Karte.")
                x = random.randint(0, len(hands[4]))
                played.append(hands[4][x])
                hands[4].pop(x)
            else:
                print(p5, "welche Karte", hands[4], "möchten Sie legen?")
                x = int(input("Bitte geben Sie die Position der Karte an,\n"
                              "die Sie legen möchten in form einer ganzen Zahl"
                              ",\nmit der ersten Karte an Position 0 an: "))
                played.append(hands[4][x])
                hands[4].pop(x)
                clear_screen()

        # establishing winner of sting
        winner = cards_functions.compare_cards(trump, played)
        print(played)
        print("Der Gewinner dieses Stiches ist: ", played[winner[0]])
        print()
        j += 1
    i += 1
