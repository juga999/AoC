from collections import defaultdict
from functools import reduce

galaxy = defaultdict(set)

weights = {}

def compute_weight(planet):
    children = galaxy[planet]
    if len(children) == 0:
        return 0

    weight = weights[planet]
    for child in children:
        weight += compute_weight(child)

    weights[planet] = weight
    return weight

def sum_weights():
    total = reduce(lambda a,b: a + b, weights.values())
    return total

def part_one():
    with open("./input.txt") as input:
        for line in input.readlines():
            objs = line.strip().split(")")
            galaxy[objs[0]].add(objs[1])
            weights[objs[0]] = len(galaxy[objs[0]])

        compute_weight('COM')
        print(sum_weights())

if __name__ == "__main__":
    part_one()
