from collections import OrderedDict

def get_path(steps):
    p = set()
    x = 0
    y = 0
    p.add((x,y))
    for step in steps:
        d = step[0]
        count = int(step[1:])
        if d == 'R':
            for i in range(count):
                p.add((x+i+1, y))
            x += count
        elif d == 'L':
            for i in range(count):
                p.add((x-i-1, y))
            x -= count
        elif d == 'U':
            for j in range(count):
                p.add((x, y+j+1))
            y +=count
        elif d == 'D':
            for j in range(count):
                p.add((x, y-j-1))
            y -= count
    return p

def part_one():
    with open("./input.txt") as input:
        wire1_steps = input.readline().split(",")
        wire2_steps = input.readline().split(",")

        path1 = get_path(wire1_steps)
        path2 = get_path(wire2_steps)
        crosses = path1.intersection(path2)
        crosses.remove((0,0))
        min_distance = 0
        the_cross = None
        for cross in crosses:
            d = abs(cross[0]) + abs(cross[1])
            if the_cross is None or d < min_distance:
                the_cross = cross
                min_distance = d

        print(the_cross)
        print(min_distance)

if __name__ == "__main__":
    part_one()
