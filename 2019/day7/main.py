from computer import Computer

def run_with_phases(codes, phases):
    computers = [Computer("Comp %d" % i) for i in range(5)]
    amplifiers = [None, None, None, None, None]
    for i in range(5):
        amplifiers[i] = computers[i].run(codes.copy())
        computers[i].set_inputs([phases[i]])
        next(amplifiers[i])

    try:
        input_signal = 0
        while True:
            computers[0].set_inputs([input_signal])
            output1 = next(amplifiers[0])
            computers[1].set_inputs([output1])
            output2 = next(amplifiers[1])
            computers[2].set_inputs([output2])
            output3 = next(amplifiers[2])
            computers[3].set_inputs([output3])
            output4 = next(amplifiers[3])
            computers[4].set_inputs([output4])
            input_signal = next(amplifiers[4])
    except StopIteration:
        pass

    return computers[4].last_output

def test():
    codes = list(map(int, "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5".split(",")))
    result = run_with_phases(codes, [9,8,7,6,5])
    print(result)

def phases_generator():
    for i in range(5, 10):
        for j in range(5, 10):
            for k in range(5, 10):
                for l in range(5, 10):
                    for m in range(5, 10):
                        phases = [i, j, k, l, m]
                        if len(set(phases)) == 5:
                            yield phases

def part_two():
    with open("./input.txt") as input:
        original_codes = list(map(int, input.readline().split(",")))

        max_result = 0
        best_phases = None
        for phases in phases_generator():
            result = run_with_phases(original_codes, phases)
            if result > max_result:
                max_result = result
                best_phases = phases

        print("------------")
        print(max_result)
        print(best_phases)

if __name__ == "__main__":
    #test()
    part_two()
