import itertools
import functools
import operator

def part1():
    with open("input.txt") as f:
        elves_calories = []
        elf_calories = 0
        for line in f.readlines():
            if len(line.strip()) > 0:
                elf_calories = elf_calories + int(line.strip())
            else:
                elves_calories.append(elf_calories)
                elf_calories = 0
        elves_calories.append(elf_calories)
        print(max(elves_calories))
        #69626

def part2():
    with open("input.txt") as f:
        elves_calories = []
        elf_calories = 0
        for line in f.readlines():
            if len(line.strip()) > 0:
                elf_calories = elf_calories + int(line.strip())
            else:
                elves_calories.append(elf_calories)
                elf_calories = 0
        elves_calories.append(elf_calories)
        elves_calories.sort(reverse=True)
        top_three_total = sum(elves_calories[0:3])
        print(top_three_total)
        # 206780

if __name__ == "__main__":
    part2()
