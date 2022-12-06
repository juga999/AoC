import itertools
import functools
import operator
import collections

def binatodeci(binary):
    return sum(val*(2**idx) for idx, val in enumerate(reversed(binary)))


def get_most_common_bits(threshold, data):
    return list(1 if sum(d) >= threshold else 0 for d in zip(*data))

def get_most_common_bit_at_col(data, col):
    counter = list(collections.Counter(d) for d in zip(*data))[col]
    if counter[0] > counter[1]:
        return 0
    else:
        return 1

def get_least_common_bit_at_col(data, col):
    counter = list(collections.Counter(d) for d in zip(*data))[col]
    if counter[0] > counter[1]:
        return 1
    else:
        return 0

def get_least_common_bits(threshold, data):
    return list(1 if sum(d) < threshold else 0 for d in zip(*data))

def filter_data(data, bit, pos):
    return list(filter(lambda entry: entry[pos] == bit, data))

def part1():
    data = []
    with open("input.txt") as f:
        for index, line in enumerate(f):
            data.append((list(int(c) for c in line.strip())))
    threshold = int(len(data) / 2)
    most_common_bits = get_most_common_bits(threshold, data)
    least_common_bits = get_least_common_bits(threshold, data)
    gamma_value = binatodeci(list(most_common_bits))
    epsilon_value = binatodeci(list(least_common_bits))
    print(gamma_value * epsilon_value)
    # 1025636

def part2():
    data = []
    with open("input.txt") as f:
        for index, line in enumerate(f):
            data.append((list(int(c) for c in line.strip())))
    oxygen_data = list(data)
    co2_data = list(data)

    pos = 0
    while len(oxygen_data) > 1:
        most_common_bit = get_most_common_bit_at_col(oxygen_data, pos)
        oxygen_data = filter_data(oxygen_data, most_common_bit, pos)
        pos = pos + 1
    oxygen_rating = binatodeci(oxygen_data[0])
    print(oxygen_rating)

    pos = 0
    while len(co2_data) > 1:
        least_common_bit = get_least_common_bit_at_col(co2_data, pos)
        co2_data = filter_data(co2_data, least_common_bit, pos)
        pos = pos + 1
    co2_rating = binatodeci(co2_data[0])
    print(co2_rating)

    print(oxygen_rating * co2_rating)
    # 793873

if __name__ == "__main__":
    part2()
