using namespace std;

#include "../utils/utils.cc"

vector<int> section_ids(const string& entry)
{
    int low;
    int high;
    vector<int> result;
    vector<string> values;
    split(entry, '-', values);
    istringstream(values[0]) >> low;
    istringstream(values[1]) >> high;
    for (int i=low; i<=high; i++) {
        result.push_back(i);
    }
    return result;
}

bool are_disjoint(const vector<int>& left, const vector<int>& right)
{
    std::vector<int> intersection;
    std::set_intersection(left.begin(), left.end(),
                          right.begin(), right.end(),
                          std::back_inserter(intersection));
    return intersection.size() == 0;
}

int main()
{
    int total = 0;
    ifstream f{"input.txt"};
    for (string line; getline(f, line); ) {
        std::vector<string> entry;
        split(line, ',', entry);
        const auto&& left = section_ids(entry[0]);
        const auto&& right = section_ids(entry[1]);
        if (!are_disjoint(left, right)) {
            total += 1;
        }
    }
    cout << total << endl;
    return 0;
}
