using namespace std;

#include "../utils/utils.cc"

int main()
{
    ifstream f{"input.txt"};
    std::vector<int> elves_calories;
    int elf_calories = 0;
    for (string line; getline(f, line); ) {
        if (line.size() > 0) {
            int value = 0;
            std::istringstream(line) >> value;
            elf_calories += value;
        } else {
            elves_calories.push_back(elf_calories);
            elf_calories = 0;
        }
    }
    elves_calories.push_back(elf_calories);
    sort(elves_calories.begin(), elves_calories.end(), greater<int>());
    int sum = accumulate(
        elves_calories.begin(),
        elves_calories.begin()+3,
        0);
    cout << sum << endl;
    return 0;
}
