import itertools
import functools
import operator

def part1():
    count = 0
    prev = None
    with open("input.txt") as f:
        values = list(map(lambda l: int(l.strip()), f.readlines()))
        for value in values:
            if prev is not None and value > prev:
                count = count + 1
            prev = value
    print(count)
    # 1400

def part2():
    count = 0
    prev = None
    with open("input.txt") as f:
        values = list(map(lambda l: int(l.strip()), f.readlines()))
        for i in range(len(values) - 2):
            s = functools.reduce(operator.add, values[i: i + 3])
            if prev is not None and s > prev:
                count = count + 1
            prev = s
        print(count)
        # 1429

if __name__ == "__main__":
    part2()
