from collections import deque

class ComputerError(Exception):
    pass

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
        self.relative_base = 0
        self.memory = dict()
        self.state = Computer.STATE_CONTINUE

    def at_memory(self, pos):
        if pos < len(self.codes):
            return self.codes[pos]
        else:
            return self.memory.get(pos, 0)

    def at(self):
        return self.at_memory(self.index)

    def at_address(self):
        return self.at_memory(self.at())

    def at_relative_address(self):
        return self.at_memory(self.at() + self.relative_base)

    def put_at_memory(self, pos, value):
        if pos < len(self.codes):
            self.codes[pos] = value
        else:
            self.memory[pos] = value

    def put_at_address(self, value):
        self.put_at_memory(self.at(), value)

    def put_at_relative_address(self, value):
        self.put_at_memory(self.at() + self.relative_base, value)

    def next(self):
        self.index += 1

    def next_n_args(self, n):
        value = self.at()
        args = []
        digit = 100
        for _ in range(n):
            self.next()
            addressing = int(value / digit) % 10
            if addressing == 0:
                args.append(self.at_address())
            elif addressing == 1:
                args.append(self.at())
            elif addressing == 2:
                args.append(self.at_relative_address())
            else:
                print("Unknown addressing %d" % addressing)
                raise ComputerError()

            digit = digit * 10

        output_addressing = int(value / digit) % 10

        return (args, output_addressing)

    def next_put_result(self, value, output_addressing=0):
        self.next()
        if output_addressing == 0:
            self.put_at_address(value)
        elif output_addressing == 2:
            self.put_at_relative_address(value)
        else:
            raise ComputerError()

    def instruction(self):
        return self.at() % 100

    def add(self):
        (args, output_addressing) = self.next_n_args(2)
        result = args[0] + args[1]
        self.next_put_result(result, output_addressing)
        self.next()

    def mult(self):
        (args, output_addressing) = self.next_n_args(2)
        result = args[0] * args[1]
        self.next_put_result(result, output_addressing)
        self.next()

    def jump_if_true(self):
        (args, output_addressing) = self.next_n_args(2)
        if output_addressing == 2:
            raise ComputerError()
        if args[0] != 0:
            self.index = args[1]
        else:
            self.next()

    def jump_if_false(self):
        (args, output_addressing) = self.next_n_args(2)
        if output_addressing == 2:
            raise ComputerError()
        if args[0] == 0:
            self.index = args[1]
        else:
            self.next()

    def less_than(self):
        (args, output_addressing) = self.next_n_args(2)
        result = 0
        if args[0] < args[1]:
            result = 1
        self.next_put_result(result, output_addressing)
        self.next()

    def equals(self):
        (args, output_addressing) = self.next_n_args(2)
        result = 0
        if args[0] == args[1]:
            result = 1
        self.next_put_result(result, output_addressing)
        self.next()

    def relative_base_offset(self):
        (args, output_addressing) = self.next_n_args(1)
        if output_addressing == 2:
            raise ComputerError()
        self.relative_base += args[0]
        self.next()

    def input(self):
        (_, output_addressing) = self.next_n_args(0)
        result = self.inputs.popleft()
        print("%s Input:" % self.name, result)
        self.next_put_result(result, output_addressing)
        self.next()

    def output(self):
        (args, _) = self.next_n_args(1)
        self.last_output = args[0]
        print("%s Output:" % self.name, self.last_output)
        self.next()

    def compute(self):
        instruction = self.instruction()
        if instruction == 99:
            self.state = Computer.STATE_EXIT
            return

        if instruction == 1:
            self.add()
        elif instruction == 2:
            self.mult()
        elif instruction == 3:
            if len(self.inputs) > 0:
                self.input()
            else:
                self.state = Computer.STATE_WAIT_INPUT
                return
        elif instruction == 4:
            self.output()
            self.state = Computer.STATE_YIELD_OUTPUT
            return
        elif instruction == 5:
            self.jump_if_true()
        elif instruction == 6:
            self.jump_if_false()
        elif instruction == 7:
            self.less_than()
        elif instruction == 8:
            self.equals()
        elif instruction == 9:
            self.relative_base_offset()
        else:
            print("%s unknown op %s" % (self.name, instruction))
            raise ComputerError()

        self.state = Computer.STATE_CONTINUE

    def set_inputs(self, values):
        self.inputs = deque(values)

    def run(self, codes):
        self.codes = codes
        self.last_output = None
        self.state = Computer.STATE_CONTINUE
        while self.state is Computer.STATE_CONTINUE:
            self.compute()
            if self.state is Computer.STATE_WAIT_INPUT:
                yield
                self.state = Computer.STATE_CONTINUE
            elif self.state is Computer.STATE_YIELD_OUTPUT:
                yield self.last_output
                self.last_output = None
                self.state = Computer.STATE_CONTINUE
        print("%s EXIT" % self.name)

