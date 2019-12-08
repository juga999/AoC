from collections import OrderedDict

def get_path(steps):
    p = OrderedDict()
    x = 0
    y = 0
    total = 0
    p[(x,y)] = 0
    for step in steps:
        d = step[0]
        count = int(step[1:])
        if d == 'R':
            for i in range(count):
                total += 1
                x += 1
                p[(x, y)] = total
        elif d == 'L':
            for i in range(count):
                total += 1
                x += -1
                p[(x, y)] = total
        elif d == 'U':
            for j in range(count):
                total += 1
                y += 1
                p[(x, y)] = total
        elif d == 'D':
            for j in range(count):
                total += 1
                y += -1
                p[(x, y)] = total
    return p

def part_two():
    with open("./input.txt") as input:
        wire1_steps = input.readline().split(",")
        wire2_steps = input.readline().split(",")

        path1 = get_path(wire1_steps)
        path2 = get_path(wire2_steps)
        crosses = set(path1.keys()).intersection(set(path2.keys()))
        crosses.remove((0,0))

        min_count = None
        for cross in crosses:
            count = path1[cross] + path2[cross]
            if min_count is None or count < min_count:
                min_count = count

        print(min_count)

if __name__ == "__main__":
    part_two()
