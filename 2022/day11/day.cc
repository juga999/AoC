using namespace std;

#include "../utils/utils.cc"

typedef uint64_t worry_level_t;


struct Monkey {
    deque<worry_level_t> items;
    function<worry_level_t(const worry_level_t&)> operation;
    function<int(worry_level_t&)> test;
    unsigned int inspections = 0;
};

template <typename T> string type_name();

uint64_t common_test_divider = 23 * 19 * 13 * 17;

void init_test(deque<Monkey>& monkeys)
{
    monkeys.push_back({
        .items = {79,98},
        .operation = [](const worry_level_t& old) {
            return (old * 19) % common_test_divider;
        },
        .test = [](worry_level_t& value) {
            if (value % 23 == 0) {
                return 2;
            } else {
                return 3;
            }
        }
    });
    monkeys.push_back({
        .items = {54,65,75,74},
        .operation = [](const worry_level_t& old) {
            return old + 6;
        },
        .test = [](worry_level_t& value) {
            if (value % 19 == 0) {
                return 2;
            } else {
                return 0;
            }
        }
    });
    monkeys.push_back({
        .items = {79,60,97},
        .operation = [](const worry_level_t& old) {
            return (old * old) % common_test_divider;
        },
        .test = [](worry_level_t& value) {
            if (value % 13 == 0) {
                return 1;
            } else {
                return 3;
            }
        }
    });
    monkeys.push_back({
        .items = {74},
        .operation = [](const worry_level_t& old) {
            return old + 3;
        },
        .test = [](worry_level_t& value) {
            if (value % 17 == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    });
}

uint64_t common_divider = 13 * 3 * 7 * 2 * 19 * 5 * 11 * 17;

void init(deque<Monkey>& monkeys)
{
    monkeys.push_back({
        .items = {89, 73, 66, 57, 64, 80},
        .operation = [](const worry_level_t& old) {
            return old * 3;
        },
        .test = [](worry_level_t& value) {
            if (value % 13 == 0) {
                return 6;
            } else {
                return 2;
            }
        }
    });
    monkeys.push_back({
        .items = {83, 78, 81, 55, 81, 59, 69},
        .operation = [](const worry_level_t& old) {
            return old + 1;
        },
        .test = [](worry_level_t& value) {
            if (value % 3 == 0) {
                return 7;
            } else {
                return 4;
            }
        }
    });
    monkeys.push_back({
        .items = {76, 91, 58, 85},
        .operation = [](const worry_level_t& old) {
            return old * 13;
        },
        .test = [](worry_level_t& value) {
            if (value % 7 == 0) {
                return 1;
            } else {
                return 4;
            }
        }
    });
    monkeys.push_back({
        .items = {71, 72, 74, 76, 68},
        .operation = [](const worry_level_t& old) {
            return old * old;
        },
        .test = [](worry_level_t& value) {
            if (value % 2 == 0) {
                return 6;
            } else {
                return 0;
            }
        }
    });
    monkeys.push_back({
        .items = {98, 85, 84},
        .operation = [](const worry_level_t& old) {
            return old + 7;
        },
        .test = [](worry_level_t& value) {
            if (value % 19 == 0) {
                return 5;
            } else {
                return 7;
            }
        }
    });
    monkeys.push_back({
        .items = {78},
        .operation = [](const worry_level_t& old) {
            return old + 8;
        },
        .test = [](worry_level_t& value) {
            if (value % 5 == 0) {
                return 3;
            } else {
                return 0;
            }
        }
    });
    monkeys.push_back({
        .items = {86, 70, 60, 88, 88, 78, 74, 83},
        .operation = [](const worry_level_t& old) {
            return old + 4;
        },
        .test = [](worry_level_t& value) {
            if (value % 11 == 0) {
                return 1;
            } else {
                return 2;
            }
        }
    });
    monkeys.push_back({
        .items = {81, 58},
        .operation = [](const worry_level_t& old) {
            return old + 5;
        },
        .test = [](worry_level_t& value) {
            if (value % 17 == 0) {
                return 3;
            } else {
                return 5;
            }
        }
    });
}

void part1()
{
    deque<Monkey> monkeys;

    init_test(monkeys);
    //init(monkeys);

    for (int r = 0; r < 20; r++) {
        for (size_t i = 0; i < monkeys.size(); i++) {
            for (deque<worry_level_t>::iterator it = monkeys[i].items.begin(); it != monkeys[i].items.end();) {
                worry_level_t item = *it;
                worry_level_t level = monkeys[i].operation(item) / 3;
                int dest = monkeys[i].test(level);
                monkeys[dest].items.push_back(level);
                it = monkeys[i].items.erase(it);
                ++monkeys[i].inspections;
            }
        }
    }
    vector<unsigned int> inspections;
    for (const auto& monkey : monkeys) {
        inspections.push_back(monkey.inspections);
    }
    sort(inspections.begin(), inspections.end(), greater<int>());
    cout << inspections[0] * inspections[1] << endl;
}

void part2()
{
    deque<Monkey> monkeys;

    //init_test(monkeys);
    //auto divider = common_test_divider;
    init(monkeys);
    auto divider = common_divider;

    const auto rounds = 10000;
    for (int r = 0; r < rounds; r++) {
        for (size_t i = 0; i < monkeys.size(); i++) {
            for (deque<worry_level_t>::iterator it = monkeys[i].items.begin(); it != monkeys[i].items.end();) {
                worry_level_t level = monkeys[i].operation(*it) % divider;
                int dest = monkeys[i].test(level);
                monkeys[dest].items.push_back(level);
                it = monkeys[i].items.erase(it);
                ++monkeys[i].inspections;
            }
        }
    }

    vector<uint64_t> inspections;
    for (const auto& monkey : monkeys) {
        inspections.push_back(monkey.inspections);
    }
    aoc::print_collection(inspections);
    sort(inspections.begin(), inspections.end(), greater<int>());
    uint64_t result = inspections[0] * inspections[1];
    cout << result << endl;
}

int main()
{
    part2();
    return 0;
}
