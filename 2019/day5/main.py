class Computer:
    def __init__(self, codes):
        self.index = 0
        self.codes = codes

    def at(self):
        return self.codes[self.index]

    def at_address(self):
        return self.codes[self.at()]

    def put_at_address(self, value):
        self.codes[self.at()] = value

    def next(self):
        self.index += 1

    def next_n_args(self, n):
        value = self.at()
        args = []
        digit = 100
        for _ in range(n):
            self.next()
            if int(value / digit) % 10 == 0:
                args.append(self.at_address())
            else:
                args.append(self.at())
            digit = digit * 10

        return args

    def next_put_result(self, value):
        self.next()
        self.put_at_address(value)

    def instruction(self):
        return self.at() % 100

    def add(self):
        args = self.next_n_args(2)
        result = args[0] + args[1]
        self.next_put_result(result)
        self.next()

    def mult(self):
        args = self.next_n_args(2)
        result = args[0] * args[1]
        self.next_put_result(result)
        self.next()

    def jump_if_true(self):
        args = self.next_n_args(2)
        if args[0] != 0:
            self.index = args[1]
        else:
            self.next()

    def jump_if_false(self):
        args = self.next_n_args(2)
        if args[0] == 0:
            self.index = args[1]
        else:
            self.next()

    def less_than(self):
        args = self.next_n_args(2)
        result = 0
        if args[0] < args[1]:
            result = 1
        self.next_put_result(result)
        self.next()

    def equals(self):
        args = self.next_n_args(2)
        result = 0
        if args[0] == args[1]:
            result = 1
        self.next_put_result(result)
        self.next()

    def input(self):
        result = int(input("Input: "))
        self.next_put_result(result)
        self.next()

    def output(self):
        self.next()
        print("Output:", self.at_address())
        self.next()

    def compute(self):
        instruction = self.instruction()
        if instruction == 99:
            return False

        if instruction == 1:
            self.add()
        elif instruction == 2:
            self.mult()
        elif instruction == 3:
            self.input()
        elif instruction == 4:
            self.output()
        elif instruction == 5:
            self.jump_if_true()
        elif instruction == 6:
            self.jump_if_false()
        elif instruction == 7:
            self.less_than()
        elif instruction == 8:
            self.equals()
        else:
            print("unknown op", instruction)
            return False

        return True

def run(codes):
    computer = Computer(codes)
    execute = True
    while execute:
        execute = computer.compute()

def part_two():
    with open("./input.txt") as input:
        original_codes = list(map(int, input.readline().split(",")))
        run(original_codes)

if __name__ == "__main__":
    part_two()
