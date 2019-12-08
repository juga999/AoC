def fuel_for_mass(m):
    return int(m / 3) - 2

def fuel_for_mass_refined(m, fuels):
    f = fuel_for_mass(m)
    if f > 0:
        fuels.append(f)
        fuel_for_mass_refined(f, fuels)

def fuel_for_stage(m):
    extra_fuel = []
    fuel_for_mass_refined(m, extra_fuel)
    return sum(extra_fuel)

def part_one():
    with open("./input.txt") as input:
        lines = input.readlines()
        total = sum(map(lambda l: fuel_for_mass((int(l))) , lines))
        print(total)

def part_two():
    with open("./input.txt") as input:
        lines = input.readlines()
        total = sum(map(lambda l: fuel_for_stage((int(l))) , lines))
        print(total)

if __name__ == "__main__":
    part_one()
    part_two()
