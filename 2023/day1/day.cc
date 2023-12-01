#include "../utils/utils.hpp"

std::map<std::string, char> SPELLED_DIGIT_MAP = {
    {"zero", '0'},
    {"one", '1'},
    {"two", '2'},
    {"three", '3'},
    {"four", '4'},
    {"five", '5'},
    {"six", '6'},
    {"seven", '7'},
    {"eight", '8'},
    {"nine", '9'}
};

char extract_spelled_digit(const std::string& line, size_t pos)
{
    for (auto& entry : SPELLED_DIGIT_MAP) {
        if (line.rfind(entry.first, pos) == pos) {
            return entry.second;
        }
    }
    return '\0';
}

auto extract_number(const std::string& line)
{
    int pos = 0;
    std::vector<char> digits;
    for (auto c : line) {
        if (c >= '0' && c <= '9') {
            digits.push_back(c);
        } else {
            char d = extract_spelled_digit(line, pos);
            if (d != '\0') {
                digits.push_back(d);
            }
        }
        ++pos;
    }
    return aoc::to_int({*digits.begin(), *digits.rbegin()});
}

int main()
{
    int sum = 0;
    std::ifstream f{"input.txt"};
    for (std::string line; std::getline(f, line); ) {
        auto value = extract_number(line);
        sum += *value;
    }
    std::cout << std::endl;
    std::cout << sum << std::endl;

    return 0;
}
