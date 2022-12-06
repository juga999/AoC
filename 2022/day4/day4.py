def section_ids(entry):
    values = entry.split("-")
    low = int(values[0])
    high = int(values[1])
    ids = set([i for i in range(low, high+1)])
    return ids

def part1():
    total = 0
    with open("input.txt") as f:
        for line in f.readlines():
            entry = line.strip().split(",")
            left = section_ids(entry[0])
            right = section_ids(entry[1])
            if left.issubset(right) or right.issubset(left):
                total = total + 1
    print(total)

def part2():
    total = 0
    with open("input.txt") as f:
        for line in f.readlines():
            entry = line.strip().split(",")
            left = section_ids(entry[0])
            right = section_ids(entry[1])
            if not left.isdisjoint(right):
                total = total + 1
    print(total)

if __name__ == "__main__":
    part2()