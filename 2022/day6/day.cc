using namespace std;

#include "../utils/utils.cc"

typedef vector<char>::iterator char_iter;

bool has_duplicates(char_iter begin, char_iter end)
{
    set<char> tmp;
    copy(begin, end, inserter(tmp, tmp.end()));
    return tmp.size() != static_cast<size_t>(end - begin);
}

void process_buffer(const string& str, size_t marker_len)
{
    vector<char> data(str.begin(), str.end());

    bool keep_processing = true;
    char_iter cursor = data.begin();
    while (keep_processing && cursor + marker_len <= data.end()) {
        keep_processing = has_duplicates(cursor, cursor + marker_len);
        ++cursor;
    }
    cout << keep_processing << "," << cursor - data.begin() - 1 + marker_len << endl;    
}

// 1544
void part1()
{
    ifstream f{"input.txt"};
    string str;
    getline(f, str);
    process_buffer(str, 4);
}

// 2145
void part2()
{
    ifstream f{"input.txt"};
    string str;
    getline(f, str);
    process_buffer(str, 14);
}

int main()
{
    part2();
    return 0;
}
