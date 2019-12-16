def layer_generator(data, width, height):
    image = [int(value) for value in data]
    image_size = len(image)
    layer_size = int(width * height)
    layer_count = int(image_size / layer_size)
    for index in range(0, layer_count):
        layer = image[index*layer_size:(index+1)*layer_size]
        yield layer

def count_digit(layer, digit):
    return len(list(filter(lambda d: d == digit, layer)))

def count_zero(layer):
    return count_digit(layer, 0)

def part_one(data, width, height):
    layers = layer_generator(data, width, height)
    layer = min(layers, key=lambda l: count_zero(l))
    ones_count = count_digit(layer, 1)
    twos_count = count_digit(layer, 2)
    print(ones_count * twos_count)

if __name__ == "__main__":
    #part_one("122456789012", 3, 2)
    with open("./input.txt") as input:
        data = input.readline()
        part_one(data, 25, 6)