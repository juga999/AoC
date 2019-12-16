def layer_generator(data, width, height):
    image_data = [int(value) for value in data]
    image_size = len(image_data)
    layer_size = int(width * height)
    layer_count = int(image_size / layer_size)
    for index in range(0, layer_count):
        layer = image_data[index*layer_size:(index+1)*layer_size]
        yield layer

def count_digit(layer, digit):
    return len(list(filter(lambda d: d == digit, layer)))

def count_zero(layer):
    return count_digit(layer, 0)

def get_pixel_value(layers, width, x, y):
    for layer in layers:
        pixel = layer[x + width*y]
        if pixel == 0 or pixel == 1:
            return pixel
    return 2

def part_two(data, width, height):
    layers = list(layer_generator(data, width, height))
    for y in range(0, height):
        for x in range(0, width):
            p = get_pixel_value(layers, width, x, y)
            if p == 0:
                print("_", end="")
            elif p == 1:
                print("X", end="")
            else:
                print(" ", end="")
        print("")

if __name__ == "__main__":
    #part_two("0222112222120000", 2, 2)
    with open("./input.txt") as input:
        data = input.readline()
        part_two(data, 25, 6)
