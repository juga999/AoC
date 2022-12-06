letters = [chr(i) for  i in range(97, 123)] + [chr(i) for  i in range(65, 91)]

def part1():
    total = 0
    with open("input.txt") as f:
        for line in f.readlines():
            entry = line.strip()
            entry_len = len(entry)
            left = set([*entry[0:int(entry_len/2)]])
            right = set([*entry[int(entry_len/2):]])
            common = left.intersection(right)
            total = total + letters.index(common.pop()) + 1
    print(total)

def part2():
    total = 0
    group  = []
    with open("input.txt") as f:
        for line in f.readlines():
            entry = line.strip()
            group.append(entry)
            if len(group) == 3:
                group1 = set([*group[0]])
                group2 = set([*group[1]])
                group3 = set([*group[2]])
                common = group1.intersection(group2.intersection(group3))
                total = total + letters.index(common.pop()) + 1
                group = []
    print(total)

if __name__ == "__main__":
    part2()
