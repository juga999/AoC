import itertools
import functools
import operator

def part1():
    hpos = 0
    depth = 0
    with open("input.txt") as f:
        for index, line in enumerate(f):
            step = line.strip().split()
            if step[0] == 'forward':
                hpos = hpos + int(step[1])
            elif step[0] == 'up':
                depth = depth - int(step[1])
            elif step[0] == 'down':
                depth = depth + int(step[1])
        print(hpos * depth)

def part2():
    aim = 0
    hpos = 0
    depth = 0
    with open("input.txt") as f:
        for index, line in enumerate(f):
            step = line.strip().split()
            if step[0] == 'forward':
                hpos = hpos + int(step[1])
                depth = depth + aim * int(step[1])
            elif step[0] == 'up':
                aim = aim - int(step[1])
            elif step[0] == 'down':
                aim = aim + int(step[1])
        print(hpos * depth)

if __name__ == "__main__":
    part2()
