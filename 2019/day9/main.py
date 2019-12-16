from computer import Computer

def test1():
    codes = list(map(int, "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99".split(",")))
    computer = Computer("Comp 1")
    runner = computer.run(codes)
    while computer.state is not Computer.STATE_EXIT:
        next(runner)

def test2():
    codes = list(map(int, "1102,34915192,34915192,7,4,7,99,0".split(",")))
    computer = Computer("Comp 1")
    runner = computer.run(codes)
    while computer.state is not Computer.STATE_EXIT:
        next(runner)

def test3():
    codes = list(map(int, "104,1125899906842624,99".split(",")))
    computer = Computer("Comp 1")
    runner = computer.run(codes)
    while computer.state is not Computer.STATE_EXIT:
        next(runner)

def part_one():
    with open("./input.txt") as input:
        original_codes = list(map(int, input.readline().split(",")))
        computer = Computer("Comp 1")
        runner = computer.run(original_codes)
        computer.set_inputs([1])
        try:
            while True:
                next(runner)
        except StopIteration:
            print("Done")
            pass

if __name__ == "__main__":
    part_one()
    #test1()
