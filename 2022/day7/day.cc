using namespace std;

#include "../utils/utils.cc"

namespace aoc {

struct File {
    File(const string& n, size_t s):name(n), size(s) {}
    string name;
    size_t size;
};

struct Dir {
    Dir(const string& n):name(n),size(0) {}

    void add_file(const File& file) {
        files.push_back(file);
    }

    void add_dir(const Dir& dir) {
        dirs.push_back(dir);
    }

    Dir* get_dir(const string& name) {
        for (size_t i=0; i < dirs.size(); i++) {
            if (dirs[i].name == name) {
                return &dirs[i];
            }
        }
        return 0;
    }

    size_t compute_size(std::function<void(const Dir&)>);

    string name;
    size_t size;
    deque<File> files;
    deque<Dir> dirs;
};

size_t Dir::compute_size(std::function<void(const Dir&)> visitor)
{
    size_t s = 0;
    for (File& file : files) {
        s += file.size;
    }
    for (Dir& dir : dirs) {
        s += dir.compute_size(visitor);
    }
    size = s;
    visitor(*this);
    return size;
}

}

size_t read_filesystem(const string& input, aoc::Dir& root)
{
    stack<aoc::Dir*> history;
    history.push(&root);

    aoc::Dir* current_dir = &root;
    ifstream f{input};
    for (string line; getline(f, line); ) {
        vector<string> input;
        aoc::split(line, ' ', input);
        if (input[0] == "$") {
            if (input[1] == "cd") {
                if (input[2] == "..") {
                    current_dir = history.top();
                    history.pop();
                    //cout << current_dir->name << endl;
                } else {
                    history.push(current_dir);
                    current_dir = current_dir->get_dir(input[2]);
                    //cout << current_dir->name << endl;
                }
            }
        } else {
            if (input[0] == "dir") {
                current_dir->add_dir(input[1]);
            } else {
                aoc::File file{input[1], static_cast<size_t>(aoc::to_int(input[0]))};
                root.size += file.size;
                current_dir->add_file(file);
            }
        }
    }

    return root.size;
}

void part1()
{
    aoc::Dir root("/");

    read_filesystem("input.txt", root);

    size_t sum = 0;
    root.compute_size([&](auto& d){
        if (d.size <= 100000) {
            sum += d.size;
        }
    });
    cout << sum << endl;
}

void part2()
{
    aoc::Dir root("/");

    read_filesystem("input.txt", root);
    size_t unused_space = 70000000 - root.size;

    const aoc::Dir* candidate_dir = &root;
    root.compute_size([&](auto& d){
        if (unused_space + d.size >= 30000000 && d.size < candidate_dir->size) {
            candidate_dir = &d;
        }
    });
    cout << candidate_dir->size << endl;
}

int main()
{
    part2();
    return 0;
}
