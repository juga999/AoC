def part1():
    fuel_burn = lambda x, p: abs(x-p)

    with open("input.txt") as f:
        numbers = sorted(list(map(lambda l: int(l.strip()), f.readline().split(","))))
        r = [sum(fuel_burn(x, i) for x in numbers) for i in range(max(numbers)+1)]
        print(min(r))

def part2():
    fuel_burn = lambda x, p: abs(x-p)*(abs(x-p) + 1)//2

    with open("input.txt") as f:
        numbers = sorted(list(map(lambda l: int(l.strip()), f.readline().split(","))))
        r = [sum(fuel_burn(x, i) for x in numbers) for i in range(max(numbers)+1)]
        print(min(r))

if __name__ == "__main__":

    part2()