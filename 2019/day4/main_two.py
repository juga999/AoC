def check_validity(password):
    numbers = [int(n) for n in str(password)]
    digits = dict()
    previous = 0
    for n in numbers:
        if n < previous:
            return False
        count = digits.get(n, 0)
        digits[n] = count + 1
        previous = n

    has_double = False
    for n, count in digits.items():
        if count == 2:
            has_double = True

    return has_double

def part_two():
    count = 0
    for password in range(152085, 670284):
        if check_validity(password):
            count += 1
    print(count)

if __name__ == "__main__":
    part_two()
