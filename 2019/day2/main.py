import copy

def compute(codes, index):
    op = codes[index]
    if op == 99:
        return False
    pos1 = codes[index+1]
    pos2 = codes[index+2]
    output = codes[index+3]
    if op == 1:
        codes[output] = codes[pos1] + codes[pos2]
    elif op == 2:
        codes[output] = codes[pos1] * codes[pos2]
    else:
        return False
    return True

def run(codes, noun, verb):
    codes[1] = noun
    codes[2] = verb
    index = 0
    while True:
        if not compute(codes, index):
            break
        index += 4

def part_two():
    with open("./input.txt") as input:
        original_codes = list(map(int, input.readline().split(",")))
        for i in range(0, 100):
            for j in range(0, 100):
                codes = copy.copy(original_codes)
                run(codes, i, j)
                if codes[0] == 19690720:
                    print(i)
                    print(j)
                    print(100*i + j)
                    return

if __name__ == "__main__":
    part_two()
