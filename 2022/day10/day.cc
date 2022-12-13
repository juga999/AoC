using namespace std;

#include "../utils/utils.cc"

struct Computer {
    Computer()
    :cycles(0), x(1) {}

    void next_cycle() {
        ++cycles;
        int line = (cycles-1) / 40;
        if (line*40 + x-1 == cycles-1 || line*40 + x == cycles-1 || line*40 + x+1 == cycles-1) {
            crt[cycles-1] = '#';
        } else {
            crt[cycles-1] = '.';
        }
    }

    int cycles;
    int x;
    char crt[40*6] = {0};
};

int compute_strength(const Computer& computer)
{
    if (computer.cycles == 20) {
        return computer.x * computer.cycles;
    } else if (computer.cycles == 60) {
        return computer.x * computer.cycles;
    } else if (computer.cycles == 100) {
        return computer.x * computer.cycles;
    } else if (computer.cycles == 140) {
        return computer.x * computer.cycles;
    } else if (computer.cycles == 180) {
        return computer.x * computer.cycles;
    } else if (computer.cycles == 220) {
        return computer.x * computer.cycles;
    } else {
        return 0;
    }
}

void part1()
{
    int strength = 0;
    Computer computer;

    int i = 0;
    ifstream f{"input.txt"};
    for (string line; getline(f, line); ) {
        vector<string> cmd;
        aoc::split(line, ' ', cmd);
        if (cmd[0] == "noop") {
            computer.next_cycle();
            strength += compute_strength(computer);
        } else if (cmd[0] == "addx") {
            int value = aoc::to_int(cmd[1]);
            computer.next_cycle();
            strength += compute_strength(computer);
            computer.next_cycle();
            strength += compute_strength(computer);
            computer.x += value;
        }
        
        ++i;
    }
    cout << strength << endl;
}

void part2()
{
    Computer computer;

    int i = 0;
    ifstream f{"input.txt"};
    for (string line; getline(f, line); ) {
        vector<string> cmd;
        aoc::split(line, ' ', cmd);
        if (cmd[0] == "noop") {
            computer.next_cycle();
        } else if (cmd[0] == "addx") {
            int value = aoc::to_int(cmd[1]);
            computer.next_cycle();
            computer.next_cycle();
            computer.x += value;
        }
        
        ++i;
    }

    for (int j=0; j<6; j++) {
        for (int k=0; k<40; k++) {
            cout << computer.crt[40*j + k];
        }
        cout << endl;
    }
    cout << endl;
}

int main()
{
    part2();
    return 0;
}