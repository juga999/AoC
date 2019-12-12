from computer import Computer

computers = [Computer() for _ in range(5)]

def run_with_phases(codes, phases):
    input_signal = 0
    for i in range(5):
        computers[i].set_inputs([phases[i], input_signal])
        computers[i].run(codes)
        input_signal = computers[i].last_output

    return computers[4].last_output

def part_one():
    with open("./input.txt") as input:
        original_codes = list(map(int, input.readline().split(",")))

        max_result = 0
        best_phases = None
        for i in range(5):
            for j in range(5):
                for k in range(5):
                    for l in range(5):
                        for m in range(5):
                            phases = [i, j, k, l, m]
                            if len(set(phases)) == 5:
                                result = run_with_phases(original_codes, phases)
                                if result > max_result:
                                    max_result = result
                                    best_phases = phases

        print(max_result)
        print(best_phases)

if __name__ == "__main__":
    part_one()
