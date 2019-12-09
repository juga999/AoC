import copy

def _get_args(codes, index, op_modes):
    args = [None, None]
    if op_modes[1] == 0:
        args[0] = codes[codes[index+1]]
    else:
        args[0] = codes[index+1]

    if op_modes[2] == 0:
        args[1] = codes[codes[index+2]]
    else:
        args[1] = codes[index+2]

    return args

def _add(codes, index, op_modes):
    #print(index, codes[index], codes[index+1], codes[index+2], codes[index+3])
    args = _get_args(codes, index, op_modes)

    codes[codes[index+3]] = args[0] + args[1]
    return 4

def _mult(codes, index, op_modes):
    #print(index, codes[index], codes[index+1], codes[index+2], codes[index+3])
    args = _get_args(codes, index, op_modes)

    codes[codes[index+3]] = args[0] * args[1]
    return 4

def _input(codes, index, op_modes):
    value = input("Input: ")
    output = codes[index+1]
    codes[output] = int(value)
    return 2

def _output(codes, index, op_modes):
    output = codes[codes[index+1]]
    print("Output:", output)
    return 2

def get_op_modes(op):
    modes = [0,0,0,0]
    modes[0] = op % 100

    modes[1] = int(op / 100) % 10
    modes[2] = int(op / 1000) % 10
    modes[3] = int(op / 10000) % 10
    return modes

def compute(codes, index):
    op_modes = get_op_modes(codes[index])
    op = op_modes[0]
    if op == 99:
        return None
    elif op == 1:
        return _add(codes, index, op_modes)
    elif op == 2:
        return _mult(codes, index, op_modes)
    elif op == 3:
        return _input(codes, index, op_modes)
    elif op == 4:
        return _output(codes, index, op_modes)
    else:
        print("unknown op", op)
        return None

def run(codes):
    index = 0
    while True:
        offset = compute(codes, index)
        if offset is None:
            break
        index += offset

def part_two():
    with open("./input.txt") as input:
        original_codes = list(map(int, input.readline().split(",")))
        run(original_codes)


if __name__ == "__main__":
    part_two()
