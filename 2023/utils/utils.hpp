#include <climits>
#include <cmath>
#include <algorithm>
#include <numeric>
#include <functional>
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

expected<int, bool> to_int(const std::string& input) {
    int value = 0;
    std::istringstream ss(input);
    ss >> value;
    if (ss.fail()) {
        return unexpected(false);
    }
    return value;
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

template<typename T>
T remove_last(std::vector<T>& values)
{
    T element = values.back();
    values.pop_back();
    return element;
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

}