using namespace std;

#include "../utils/utils.cc"

/*
vector<vector<char>> supplies = {
    {'Z', 'N'},
    {'M', 'C', 'D'},
    {'P'}
};
*/

vector<vector<char>> supplies = {
    {'R', 'P', 'C', 'D', 'B', 'G'},
    {'H', 'V', 'G'},
    {'N', 'S', 'Q', 'D', 'J', 'P', 'M'},
    {'P', 'S', 'L', 'G', 'D', 'C', 'N', 'M'},
    {'J', 'B', 'N', 'C', 'P', 'F', 'L', 'S'},
    {'Q', 'B', 'D', 'Z', 'V', 'G', 'T', 'S'},
    {'B', 'Z', 'M', 'H', 'F', 'T', 'Q'},
    {'C', 'M', 'D', 'B', 'F'},
    {'F', 'C', 'Q', 'G'}
};

void get_step(const string& entry, vector<int>& step)
{
    vector<string> instructions;
    aoc::split(entry, ' ', instructions);
    step.push_back(aoc::to_int(instructions[1]));
    step.push_back(aoc::to_int(instructions[3]) - 1);
    step.push_back(aoc::to_int(instructions[5]) - 1);
}

void part1()
{
    vector<int> step;
    ifstream f{"input_test.txt"};
    for (string line; getline(f, line); ) {
        step.clear();
        get_step(line, step);
        int q = step[0];
        int src = step[1];
        int dest = step[2];
        while (q > 0) {
            supplies[dest].push_back(aoc::remove_last(supplies[src]));
            q--;
        }
    }
    for (auto&& stack: supplies) {
        cout << stack.back();
    }
    cout << endl;
}

// FGLQJCMBD
void part2()
{
    vector<int> step;
    ifstream f{"input.txt"};
    for (string line; getline(f, line); ) {
        step.clear();
        get_step(line, step);
        int q = step[0];
        int src = step[1];
        int dest = step[2];
        vector<char> group;
        while (q > 0) {
            group.insert(group.begin(), aoc::remove_last(supplies[src]));
            q--;
        }
        copy(group.begin(), group.end(), back_inserter(supplies[dest]));
    }
    for (auto&& stack: supplies) {
        cout << stack.back();
    }
    cout << endl;
}

int main()
{
    part2();

    return 0;
}