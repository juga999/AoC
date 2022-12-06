import itertools
import functools
import operator

def get_hand_score(hand):
    if hand == 'X':
        return 1
    elif hand == 'Y':
        return 2
    elif hand == 'Z':
        return 3
    else:
        return 0

def get_outcome_score(outcome):
    if outcome == 'Z':
        return 6
    elif outcome == 'Y':
        return 3
    else:
        return 0

# rock: X, A
# paper: Y ,B
# scissors: Z ,C

winning_hands = ["XC", "YA", "ZB"]
draw_hands = ["XA", "YB", "ZC"]
loosing_hands = ["XB", "YC", "ZA"]

strategy = {
    "A X": "Z",
    "A Y": "X",
    "A Z": "Y",
    "B X": "X",
    "B Y": "Y",
    "B Z": "Z",
    "C X": "Y",
    "C Y": "Z",
    "C Z": "X"
}

def part1():
    score = 0
    with open("input.txt") as f:
        for line in f.readlines():
            data = line.split()
            game_round = data[1] + data[0]
            score = score + get_hand_score(data[1])
            if game_round in winning_hands:
                score = score + 6
            elif game_round in draw_hands:
                score = score + 3
        print(score)

def part2():
    score = 0
    with open("input.txt") as f:
        for line in f.readlines():
            me = strategy[line.strip()]
            score = score + get_hand_score(me) + get_outcome_score(line.split()[1])
    print(score)

if __name__ == "__main__":
    part2()
