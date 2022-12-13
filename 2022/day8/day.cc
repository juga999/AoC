using namespace std;

#include "../utils/utils.cc"

struct Forest {
    Forest():width(0), height(0) {
        data = new int[1024*1024];
    }
    ~Forest() {
        delete[] data;
    }

    void fill(const string& s) {
        int row = 0;
        int col = 0;
        ifstream f{s};
        for (string line; getline(f, line); ) {
            col = 0;
            for(char c : line) {
                data[row*width + col] = aoc::to_int(std::string{c});
                ++col;
            }
            if (width == 0) {
                width = col;
            }
            ++row;
        }
        height = row;
    }

    int get_tree_height(int row, int col) {
        return data[row* width + col];
    }

    int get_score_right(int row, int col) {
        int cur_height = get_tree_height(row, col);
        int trees = 0;
        int h = -1;
        for (int i = 1; col+i < width && h < cur_height; i++) {
            h = get_tree_height(row, col+i);
            ++trees;
        }
        return trees;
    }

    int get_score_up(int row, int col) {
        int cur_height = get_tree_height(row, col);
        int trees = 0;
        int h = -1;
        for (int i = 1; row-i >= 0 && h < cur_height; i++) {
            h = get_tree_height(row-i, col);
            ++trees;
        }
        return trees;
    }

    int get_score_left(int row, int col) {
        int cur_height = get_tree_height(row, col);
        int trees = 0;
        int h = -1;
        for (int i = 1; col-i >= 0 && h < cur_height; i++) {
            h = get_tree_height(row, col-i);
            ++trees;
        }
        return trees;
    }

    int get_score_down(int row, int col) {
        int cur_height = get_tree_height(row, col);
        int trees = 0;
        int h = -1;
        for (int i = 1; row+i < height && h < cur_height; i++) {
            h = get_tree_height(row+i, col);
            ++trees;
        }
        return trees;
    }

    int width;
    int height;
    int* data;
};

string to_str(int row, int col) {
    ostringstream oss;
    oss << row << "," << col;
    return oss.str();
}

void part1()
{
    Forest forest;
    forest.fill("input.txt");

    map<string, bool> visibility_map;

    for (int j=0; j < forest.height; j++) {
        int max_height = -1;
        for (int i=0; i < forest.width; i++) {
            const int h = forest.get_tree_height(j, i);
            if (h > max_height) {
                visibility_map[to_str(j, i)] = true;
                max_height = h;
            }
        }
        max_height = -1;
        for (int i=forest.width-1; i >= 0; i--) {
            const int h = forest.get_tree_height(j, i);
            if (h > max_height) {
                visibility_map[to_str(j, i)] = true;
                max_height = h;
            }
        }
    }
    for (int i=0; i < forest.width; i++) {
        int max_height = -1;
        for (int j=0; j < forest.height; j++) {
            const int h = forest.get_tree_height(j, i);
            if (h > max_height) {
                visibility_map[to_str(j, i)] = true;
                max_height = h;
            }
        }
        max_height = -1;
        for (int j=forest.height-1; j >= 0; j--) {
            int h = forest.get_tree_height(j, i);
            if (h > max_height) {
                visibility_map[to_str(j, i)] = true;
                max_height = h;
            }
        }
    }
    cout << visibility_map.size() << endl;
}

void part2()
{
    Forest forest;
    forest.fill("input.txt");

    int max_score = -1;
    for (int j=0; j < forest.height; j++) {
        for (int i=0; i < forest.width; i++) {
            int score = 
                forest.get_score_right(j, i) 
                * forest.get_score_left(j, i)
                * forest.get_score_down(j, i)
                * forest.get_score_up(j, i);
            if (score > max_score) {
                max_score = score;
            }
        }
    }
    cout << max_score << endl;
}


int main()
{
    part2();
    return 0;
}
