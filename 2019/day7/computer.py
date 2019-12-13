from collections import deque

class Computer:

    STATE_CONTINUE = 1
    STATE_WAIT_INPUT = 2
    STATE_YIELD_OUTPUT = 3
    STATE_EXIT = 4

    def __init__(self, name):
        self.name = name
        self.inputs = deque([])
        self.last_output = None
        self.codes = None
        self.index = 0

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
        result = self.inputs.popleft()
        print("%s Input:" % self.name, result)
        self.next_put_result(result)
        self.next()

    def output(self):
        self.next()
        self.last_output = self.at_address()
        print("%s Output:" % self.name, self.last_output)
        self.next()

    def compute(self):
        instruction = self.instruction()
        if instruction == 99:
            return Computer.STATE_EXIT

        if instruction == 1:
            self.add()
        elif instruction == 2:
            self.mult()
        elif instruction == 3:
            if len(self.inputs) > 0:
                self.input()
            else:
                return Computer.STATE_WAIT_INPUT
        elif instruction == 4:
            self.output()
            return Computer.STATE_YIELD_OUTPUT
        elif instruction == 5:
            self.jump_if_true()
        elif instruction == 6:
            self.jump_if_false()
        elif instruction == 7:
            self.less_than()
        elif instruction == 8:
            self.equals()
        else:
            print("%s unknown op %s" % (self.name, instruction))
            return Computer.STATE_EXIT

        return Computer.STATE_CONTINUE

    def set_inputs(self, values):
        self.inputs = deque(values)

    def run(self, codes):
        self.codes = codes
        self.last_output = None
        state = Computer.STATE_CONTINUE
        while state is Computer.STATE_CONTINUE:
            state = self.compute()
            if state is Computer.STATE_WAIT_INPUT:
                yield
                state = Computer.STATE_CONTINUE
            elif state is Computer.STATE_YIELD_OUTPUT:
                yield self.last_output
                self.last_output = None
                state = Computer.STATE_CONTINUE
        print("%s EXIT" % self.name)

