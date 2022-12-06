#include <algorithm>
#include <numeric>
#include <functional>
#include <iterator>
#include <string>
#include <sstream>
#include <fstream>
#include <iostream>
#include <vector>
#include <set>
#include <stack>
#include <map>
#include <tuple>

namespace aoc {

template<class T>
void print_collection(const T& v)
{
    cout << "[ ";
    for (auto&& elem : v) {
        cout << elem << " ";
    }
    cout << "]" << endl;
}

int to_int(const string& input) {
    int value = 0;
    istringstream(input) >> value;
    return value;
}

void split(string const& str, const char delim,
            std::vector<string>& out)
{
    stringstream ss(str);
 
    string s;
    while (getline(ss, s, delim)) {
        out.push_back(s);
    }
}

template<typename T>
T remove_last(vector<T>& values)
{
    T element = values.back();
    values.pop_back();
    return element;
}

}