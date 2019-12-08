def fuel_for_mass(m):
    return int(m / 3) - 2

def main():
    with open("./input.txt") as input:
        lines = input.readlines()
        total = sum(map(lambda l: fuel_for_mass((int(l))) , lines))
        print(total)

if __name__ == "__main__":
    main()
