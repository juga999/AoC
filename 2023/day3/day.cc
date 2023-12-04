#include "../utils/utils.hpp"

typedef std::vector<std::string> str_vec_t;

struct Number {
    Number(uint value_p, int x_p, int y_p, size_t len_p)
        :value(value_p)
        ,x(std::make_unsigned_t<int>(x_p))
        ,y(std::make_unsigned_t<int>(y_p))
        ,len(len_p) {}
    uint value;
    uint x;
    uint y;
    size_t len;
};

typedef std::tuple<uint, uint> coord_t;
typedef std::optional<coord_t> maybe_coord_t;

template<uint W, uint H>
struct Matrix2D {
    static constexpr auto WIDTH = W;
    static constexpr auto HEIGHT = H;

    char data[W][H];
};

//static constexpr auto INPUT_WIDTH = 3;
//static constexpr auto INPUT_HEIGHT = 3;
//static constexpr auto INPUT_WIDTH = 10;
//static constexpr auto INPUT_HEIGHT = 10;
static constexpr auto INPUT_WIDTH = 140;
static constexpr auto INPUT_HEIGHT = 140;

struct Schema: Matrix2D<INPUT_WIDTH, INPUT_HEIGHT> {
    void init(std::ifstream& f);

    bool is_digit(char c) {
        return c >= '0' && c <= '9';
    }

    bool is_symbol(uint x, uint y) {
        return data[x][y] != '.';
    }

    bool is_gear(uint x, uint y) {
        return data[x][y] == '*';
    }

    maybe_coord_t is_adjacent(const Number& number, std::function<bool(uint, uint)> predicate);

    std::vector<Number> numbers;

    std::map<std::string, std::vector<Number>> gears;
};

void Schema::init(std::ifstream& f)
{
    uint x = 0;
    uint y = 0;
    int nb_x = -1;
    int nb_y = -1;
    std::string digits;
    for (std::string line; std::getline(f, line); ) {
        for (auto c : line) {
            data[x][y] = c;
            if (is_digit(c)) {
                if (nb_x == -1) {
                    nb_x = x;
                    nb_y = y;
                }
                digits.push_back(c);
            } else {
                if (nb_x != -1) {
                    uint value = *aoc::to_int(digits);
                    numbers.push_back({value, nb_x, nb_y, digits.size()});
                    digits.clear();
                    nb_x = -1;
                    nb_y = -1;
                }
            }
            ++x;
        }
        if (nb_x != -1) {
            uint value = *aoc::to_int(digits);
            numbers.push_back({value, nb_x, nb_y, digits.size()});
            digits.clear();
            nb_x = -1;
            nb_y = -1;
        }
        x = 0;
        ++y;
    }
}

maybe_coord_t Schema::is_adjacent(const Number& number, std::function<bool(uint, uint)> predicate)
{
    uint x_start;
    uint x_end;

    if (number.x > 0) {
        x_start = number.x - 1;
        if (predicate(x_start, number.y)) {
            return coord_t{x_start, number.y};
        }
    } else {
        x_start = 0;
    }

    if (number.x + number.len < WIDTH) {
        x_end = number.x + number.len;
        if (predicate(x_end, number.y)) {
            return coord_t{x_end, number.y};
        }
    } else {
        x_end = WIDTH - 1;
    }

    if (number.y > 0) {
        for (auto x = x_start; x <= x_end; x++) {
            if (predicate(x, number.y - 1)) {
                return coord_t{x, number.y - 1};
            }
        }
    }

    if (number.y < HEIGHT - 1) {
        for (auto x = x_start; x <= x_end; x++) {
            if (predicate(x, number.y + 1)) {
                return coord_t{x, number.y + 1};
            }
        }
    }
    return {};
}

int main()
{
    std::unique_ptr<Schema> schema = std::make_unique<Schema>();

    //std::ifstream f{"input_test.txt"};
    std::ifstream f{"input.txt"};
    schema->init(f);
    uint sum = 0;

    for (auto&& number : schema->numbers) {
        if (schema->is_adjacent(
                number, 
                [&schema](auto x, auto y) { return schema->is_symbol(x, y);})) {
            sum += number.value;
        }
    }
    std::cout << std::endl;
    std::cout << sum << std::endl; // 554003
    std::cout << std::endl;

    uint ratio = 0;
    for (auto&& number : schema->numbers) {
        auto maybe_gear = schema->is_adjacent(
            number, 
            [&schema](auto x, auto y) { return schema->is_gear(x, y);});
        if (maybe_gear) {
            const auto [x, y] = maybe_gear.value();
            std::string coords = std::to_string(x) + "," + std::to_string(y);
            schema->gears[coords].push_back(number);
        }
    }
    for (auto [coords, numbers] : schema->gears) {
        if (numbers.size() == 2) {
            ratio += numbers[0].value * numbers[1].value;
        }
    }
    std::cout << ratio << std::endl; // 87263515
    std::cout << std::endl;

    return 0;
}
