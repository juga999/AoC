#include <climits>
#include <cmath>
#include <algorithm>
#include <numeric>
#include <functional>
#include <optional>
#include <ranges>
#include <iterator>
#include <string>
#include <sstream>
#include <fstream>
#include <iostream>
#include <vector>
#include <deque>
#include <list>
#include <set>
#include <stack>
#include <map>
#include <tuple>
#include <regex>

// https://github.com/TartanLlama/expected
#include "expected.hpp"

using tl::expected;
using tl::unexpected;

namespace aoc {

const std::string WHITESPACE = " \n\r\t\f\v";

template<typename T>
void print_collection(const T& v)
{
    std::cout << "[ ";
    for (auto&& elem : v) {
        std::cout << elem << " ";
    }
    std::cout << "]" << std::endl;
}

template<typename T>
expected<T, bool> to_number(const std::string& input) {
    T value = 0;
    std::istringstream ss(input);
    ss >> value;
    if (ss.fail()) {
        return unexpected(false);
    }
    return value;
}

expected<int, bool> to_int(const std::string& input) {
    return to_number<int>(input);
}

expected<uint, bool> to_uint(const std::string& input) {
    return to_number<uint>(input);
}

void split(const std::string& str, const char delim,
            std::vector<std::string>& out)
{
    std::stringstream ss(str);

    std::string s;
    while (std::getline(ss, s, delim)) {
        out.push_back(s);
    }
}

void split(const std::string& str, const char delim,
            std::function<void(const std::string&, uint pos)> consumer)
{
    std::stringstream ss(str);

    uint pos = 0;
    std::string s;
    while (std::getline(ss, s, delim)) {
        consumer(s, pos);
        ++pos;
    }
}

expected<std::string,bool> nth(const std::string& str, const char delim, uint at)
{
    std::stringstream ss(str);

    uint pos = 0;
    std::string s;
    while (std::getline(ss, s, delim)) {
        if (pos == at) {
            return s;
        }
        ++pos;
    }
    return unexpected(false);
}

std::string ltrim(const std::string& s)
{
    size_t start = s.find_first_not_of(WHITESPACE);
    return (start == std::string::npos) ? "" : s.substr(start);
}

std::string rtrim(const std::string& s)
{
    size_t end = s.find_last_not_of(WHITESPACE);
    return (end == std::string::npos) ? "" : s.substr(0, end + 1);
}

std::string trim(const std::string& s)
{
    return rtrim(ltrim(s));
}

const std::regex labelled_value_regex("(\\w+)");

expected<std::tuple<std::string, uint>, bool> labelled_uint(const std::string& str)
{
    auto match_begin = std::sregex_iterator(
        str.begin(), str.end(), labelled_value_regex);
    auto match_end = std::sregex_iterator();
    if (std::distance(match_begin, match_end) == 2) {
        std::sregex_iterator i = match_begin;
        std::string label = (*i).str();
        ++i;
        std::string number = (*i).str();
        return aoc::to_uint(aoc::trim(number))
            .map([&](auto n) { return std::make_tuple(label, n); });
    }  else {
        return unexpected(false);
    }
}

template<typename T>
T remove_last(std::vector<T>& values)
{
    T element = values.back();
    values.pop_back();
    return element;
}

}