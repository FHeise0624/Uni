"""
This Programm sets the basic functions for the card game "Wizard".
"""
__author__ = "7977844, Tams", "8012467, Ahmadzai"
__credits__ = '''Thanks for the insight on how to sort a list of dicts by key.
https://stackoverflow.com/questions/48568833/sort-a-list-of-dicts-by-each-dicts-key
,Thanks for the insight on howto get the index of a list element. 
https://datagy.io/python-index-of-max-item-list/
'''

import random


# creating a list of 52 cards
def create_card():
    """
    This function creates a deck of 52 regular playing cards.
    :return: list of strings representing the 52 card deck.
    """
    # List for the card colors and values
    colors = ["Kreuz", "Pik", "Karo", "Herz"]
    values = ["2", "3", "4", "5", "6", "7", "8", "9", "10", "Bube", "Dame",
              "König", "Ass"]
    cards = []
    # creates cards as dictionary in colour:values pairs.
    for j in range(0, len(colors)):
        for i in values:
            cards.append({colors[j]: i})
    return cards


# dealing cards to a number of players between 2 and 5 according to the
# round-number
def deal_cards(cards, rounds, player_num):
    """
    This function takes in a list of cards, the amount of cards that should
    be delt and the amount of players and returns the hand of each player.
    :param cards: List of cards, consisting of dictionary's.
    :param rounds: int that indicates the amount of cards that should
    be delt according to round number
    :param player_num: number of players that cards need to be delt to.
    :return: rest of cards in pile, hand of each player
    """
    # setting necessary lists for player hands, count variable & shuffle deck.
    j = 0
    # players = []
    hand_p1 = []
    hand_p2 = []
    hand_p3 = []
    hand_p4 = []
    hand_p5 = []
    random.shuffle(cards)

    # dealing cards, depending on player number and round number.
    if player_num == 2:
        while j < rounds:
            hand_p1.append(cards[0])
            cards.pop(0)
            hand_p2.append(cards[0])
            cards.pop(0)
            j += 1
        players = (hand_p1, hand_p2)
    elif player_num == 3:
        while j < rounds:
            hand_p1.append(cards[0])
            cards.pop(0)
            hand_p2.append(cards[0])
            cards.pop(0)
            hand_p3.append(cards[0])
            cards.pop(0)
            j += 1
        players = (hand_p1, hand_p2, hand_p3)
    elif player_num == 4:
        while j < rounds:
            hand_p1.append(cards[0])
            cards.pop(0)
            hand_p2.append(cards[0])
            cards.pop(0)
            hand_p3.append(cards[0])
            cards.pop(0)
            hand_p4.append(cards[0])
            cards.pop(0)
            j += 1
        players = (hand_p1, hand_p2, hand_p3, hand_p4)
    elif player_num == 5:
        while j < rounds:
            hand_p1.append(cards[0])
            cards.pop(0)
            hand_p2.append(cards[0])
            cards.pop(0)
            hand_p3.append(cards[0])
            cards.pop(0)
            hand_p4.append(cards[0])
            cards.pop(0)
            hand_p5.append(cards[0])
            cards.pop(0)
            j += 1
        players = (hand_p1, hand_p2, hand_p3, hand_p4, hand_p5)
    # return of a list of player hands
    return players


# comparing played cards to determine winner
def compare_cards(trump, cards_played):
    """
    This function takes in a list of cards, compares them by colour and
    value and returns the index of the highest valued card.
    :param: cards_played: list of played cards.
    :param: trump: string that states the card colour that is trump
    :return: index of the highest valued card.
    """
    # setting necessary lists for comparison
    kreuz = []
    pik = []
    karo = []
    herz = []

    # sorting played cards by the colours, while maintaining their position
    # in the card pile, filling the other spaces in the sorted piles with 0
    # as space-holder.
    for j in range(0, len(cards_played)):
        x = cards_played[j].get("Kreuz")
        if x == "Ass":
            kreuz.append(14)
        elif x == "König":
            kreuz.append(13)
        elif x == "Dame":
            kreuz.append(12)
        elif x == "Bube":
            kreuz.append(11)
        elif x is None:
            kreuz.append(0)
        else:
            kreuz.append(int(x))
        j += 1
    for k in range(0, len(cards_played)):
        x = cards_played[k].get("Pik")
        if x == "Ass":
            pik.append(14)
        elif x == "König":
            pik.append(13)
        elif x == "Dame":
            pik.append(12)
        elif x == "Bube":
            pik.append(11)
        elif x is None:
            pik.append(0)
        else:
            pik.append(int(x))
        k += 1

    for n in range(0, len(cards_played)):
        x = cards_played[n].get("Karo")
        if x == "Ass":
            karo.append(14)
        elif x == "König":
            karo.append(13)
        elif x == "Dame":
            karo.append(12)
        elif x == "Bube":
            karo.append(11)
        elif x is None:
            karo.append(0)
        else:
            karo.append(int(x))
        n += 1

    for m in range(0, len(cards_played)):
        x = cards_played[m].get("Herz")
        if x == "Ass":
            herz.append(14)
        elif x == "König":
            herz.append(13)
        elif x == "Dame":
            herz.append(12)
        elif x == "Bube":
            herz.append(11)
        elif x is None:
            herz.append(0)
        else:
            herz.append(int(x))
        m += 1

    # determining the highest valued played card per color.
    kreuz_max = kreuz.index(max(kreuz))
    pik_max = pik.index(max(pik))
    karo_max = karo.index(max(karo))
    herz_max = herz.index(max(herz))

    # comparing cards by colour and value ind regard of the trump colour.
    if trump == "Kreuz":
        if kreuz[kreuz_max] != "0":
            win = (kreuz_max, kreuz[kreuz_max])
        else:
            if pik[pik_max] != "0":
                win = (pik_max, pik[pik_max])
            else:
                if karo[karo_max] != "0":
                    win = (karo_max, karo[karo_max])
                else:
                    win = (herz_max, herz[herz_max])

    elif trump == "Pik":
        if pik[pik_max] != "0":
            win = (pik_max, pik[pik_max])
        else:
            if kreuz[kreuz_max] != "0":
                win = (kreuz_max, kreuz[kreuz_max])
            else:
                if karo[karo_max] != "0":
                    win = (karo_max, karo[karo_max])
                else:
                    win = (herz_max, herz[herz_max])

    elif trump == "Karo":
        if karo[karo_max] != "0":
            win = (karo_max, karo[karo_max])
        else:
            if kreuz[kreuz_max] != "0":
                win = (kreuz_max, kreuz[kreuz_max])
            else:
                if pik[pik_max] != "0":
                    win = (pik_max, pik[pik_max])
                else:
                    win = (herz_max, herz[herz_max])
    else:
        if herz[herz_max] != "0":
            win = (herz_max, herz[herz_max])
        else:
            if kreuz[kreuz_max] != "0":
                win = (kreuz_max, kreuz[kreuz_max])
            else:
                if pik[pik_max] != "0":
                    win = (pik_max, pik[pik_max])
                else:
                    win = (karo_max, karo[karo_max])
    return win


if __name__ == "__main__":
    # Test of create_card function:
    print("Test function 1: ")
    deck = create_card()
    print(deck)
    print(len(deck))
    print()

    # Test of deal_cards function
    print("Test function 2:")
    player_hands = deal_cards(deck, 9, 5)
    # Hand of each player
    player1 = player_hands[0]
    player2 = player_hands[1]
    player3 = player_hands[2]
    player4 = player_hands[3]
    player5 = player_hands[4]
    print("Player 1: ", player1)
    print("Player 2: ", player2)
    print("Player 3: ", player3)
    print("Player 4: ", player4)
    print("Player 5: ", player5)
    print()

    # Test of compare_cards function:
    print("Test function 3:")
    played_cards = [player1[0], player2[0], player3[0], player4[0], player5[0]]
    trumpfe = ["Kreuz", "Pik", "Karo", "Herz"]
    trumpf = random.choice(trumpfe)
    winning_card = compare_cards(trumpf, played_cards)
    print(played_cards)
    print(winning_card)
    print(played_cards[winning_card[0]])
