import re
import math

class Point(object):
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __str__(self):
        return "%d,%d" % (self.x, self.y)

def update_world(world, src, dest):
    if dest.x > src.x and dest.y == src.y:
        r = range(src.x, dest.x+1)
        for i in r:
            k = "%d,%d" % (i, src.y)
            v = world.get(k, 0)
            world[k] = v + 1
    elif dest.x < src.x and dest.y == src.y:
        r = range(dest.x, src.x+1)
        for i in r:
            k = "%d,%d" % (i, src.y)
            v = world.get(k, 0)
            world[k] = v + 1
    elif dest.y > src.y and dest.x == src.x:
        r = range(src.y, dest.y+1)
        for i in r:
            k = "%d,%d" % (src.x, i)
            v = world.get(k, 0)
            world[k] = v + 1
    elif dest.y < src.y and dest.x == src.x:
        r = range(dest.y, src.y+1)
        for i in r:
            k = "%d,%d" % (src.x, i)
            v = world.get(k, 0)
            world[k] = v + 1

def update_world_part2(world, src, dest):
    a = int(math.degrees(math.atan2(dest.y - src.y, dest.x - src.x)))

    if a == 0:
        dx = 1
        dy = 0
    elif a == 180:
        dx = -1
        dy = 0
    elif a == 90:
        dx = 0
        dy = 1
    elif a == -90:
        dx = 0
        dy = -1
    elif a == 45:
        dx = 1
        dy = 1
    elif a == -45:
        dx = 1
        dy = -1
    elif a == 135:
        dx = -1
        dy = 1
    elif a == -135:
        dx = -1
        dy = -1

    x = src.x
    y = src.y
    while True:
        k = "%d,%d" % (x, y)
        v = world.get(k, 0)
        world[k] = v + 1
        x = x + dx
        y = y + dy
        if x == dest.x and y == dest.y:
            break
    k = "%d,%d" % (dest.x, dest.y)
    v = world.get(k, 0)
    world[k] = v + 1


def part1():
    world = dict()
    with open("input.txt") as f:
        for index, line in enumerate(f):
            m = re.match(r"(\d+),(\d+) -> (\d+),(\d+)", line)
            src = Point(int(m[1]), int(m[2]))
            dest = Point(int(m[3]), int(m[4]))
            update_world(world, src, dest)
        print(len(list(filter(lambda v: v > 1, world.values()))))

def part2():
    world = dict()
    with open("input.txt") as f:
        for index, line in enumerate(f):
            m = re.match(r"(\d+),(\d+) -> (\d+),(\d+)", line)
            src = Point(int(m[1]), int(m[2]))
            dest = Point(int(m[3]), int(m[4]))
            update_world_part2(world, src, dest)
        print(len(list(filter(lambda v: v > 1, world.values()))))

if __name__ == "__main__":
    part2()