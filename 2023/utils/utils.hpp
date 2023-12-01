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

}