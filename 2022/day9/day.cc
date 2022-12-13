using namespace std;

#include "../utils/utils.cc"

struct Knot {
    Knot():x(0),y(0) {}

    bool touches(const Knot& k) {
        return abs(x - k.x) <= 1 && abs(y - k.y) <= 1;
    }

    void move_close_to(const Knot& k) {
        if (k.x - x > 0) {
            x += 1;
        } else if (k.x - x < 0) {
            x -= 1;
        }
        if (k.y - y > 0) {
            y += 1;
        } else if (k.y - y < 0) {
            y -= 1;
        }
    }

    int x;
    int y;
};

string to_str(int x, int y) {
    ostringstream oss;
    oss << x << "," << y;
    return oss.str();
}

void move_rope(const string& input, deque<Knot>& rope, set<string>& visits)
{
    int last = rope.size()-1;
    visits.insert(to_str(rope[last].x, rope[last].y));

    ifstream f{input};
    for (string line; getline(f, line); ) {
        vector<string> cmd;
        aoc::split(line, ' ', cmd);
        string direction = cmd[0];
        int count = aoc::to_int(cmd[1]);
        int s = 0;
        while (s < count) {
            if (direction == "R") {
                ++rope[0].x;
            } else if (direction == "L") {
                --rope[0].x;
            } else if (direction == "U") {
                ++rope[0].y;
            } else if (direction == "D") {
                --rope[0].y;
            }
            for (size_t i=1; i < rope.size(); i++) {
                if (!rope[i].touches(rope[i-1])) {
                    rope[i].move_close_to(rope[i-1]);
                }
            }
            visits.insert(to_str(rope[last].x, rope[last].y));
            ++s;
        }
    }
}

void part1()
{
    set<string> visits;

    deque<Knot> rope;
    for (int i=0; i < 2; i++) {
        rope.push_back(Knot());
    }

    move_rope("input.txt", rope, visits);
    cout << visits.size() << endl;
}

void part2()
{
    set<string> visits;

    deque<Knot> rope;
    for (int i=0; i < 10; i++) {
        rope.push_back(Knot());
    }

    move_rope("input.txt", rope, visits);
    cout << visits.size() << endl;
}

int main()
{
    part2();
    return 0;
}