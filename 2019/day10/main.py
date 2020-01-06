import math
import itertools

def dot_product(v1, v2):
    return v1[0]*v2[0] + v1[1]*v2[1] 

def cross_product(v1, v2):
    return v1[0]*v2[1] - v1[1]*v2[0]

def have_same_direction(v1, v2):
    return cross_product(v1, v2) == 0 and dot_product(v1, v2) > 0

def vector_length(v):
    return math.sqrt(v[0] ** 2 + v[1] ** 2)

def get_vectors(asteroid, asteroids):
    in_sight = dict()
    for other in asteroids.keys():
        if other == asteroid:
            continue
        dx = other[0] - asteroid[0]
        dy = other[1] - asteroid[1]
        in_sight[other] = (dx, dy, vector_length((dx, dy)))

    directions = in_sight.copy()
    for a1, a2 in itertools.product(directions.items(), directions.items()):
        if a1[1] == a2[1]:
            continue
        if have_same_direction(a1[1], a2[1]):
            if a1[1][2] < a2[1][2]:
                in_sight.pop(a2[0], None)
            else:
                in_sight.pop(a1[0], None)

    asteroids[asteroid] = in_sight

def part_one():
    asteroids = {}
    with open("./input.txt") as input:
        x = 0
        y = 0
        for line in input.readlines():
            for c in line.strip():
                if c == '#':
                    asteroids[(x, y)] = dict()
                x += 1
            x = 0
            y += 1

    for asteroid in asteroids.keys():
        get_vectors(asteroid, asteroids)

    best_asteroid = None
    max_neighbors = 0
    for asteroid, neighbors in asteroids.items():
        if len(neighbors.keys()) > max_neighbors:
            max_neighbors = len(neighbors.keys())
            best_asteroid = asteroid
    
    print(best_asteroid)
    print(max_neighbors)

if __name__ == "__main__":
    part_one()
