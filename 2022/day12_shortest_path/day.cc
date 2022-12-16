using namespace std;

#include "../utils/utils.cc"

struct Pos {
    Pos(size_t c, size_t r):col(c), row(r) {}
    size_t col;
    size_t row;
};

ostream& operator<<(ostream& out, const Pos& pos) {
    out << pos.col << "," << pos.row;
    return out;
}

struct World {
    World() {
        data = new char[1024*1024];
    }

    ~World() {
        delete[] data;
    }

    Pos to_pos(size_t i) const {
        size_t col = i % cols;
        size_t row = i / cols;
        return Pos{col,row};
    }

    char get(size_t i) const {
        return data[i];
    }

    char get(const Pos& pos) const {
        return get(pos.col, pos.row);
    }

    char get(size_t col, size_t row) const {
        return get(row * cols + col);
    }

    bool neighbors(size_t a, size_t b) const {
        if (a == b) {
            return false;
        }

        auto pos1 = to_pos(a);
        auto pos2 = to_pos(b);
        if (pos1.col != pos2.col && pos1.row != pos2.row) {
            return false;
        }
        int dc = pos1.col - pos2.col;
        int dr = pos1.row - pos2.row;
        return abs(dc) <= 1 && abs(dr) <= 1;
    }

    bool get_left(size_t i, char& c) {
        auto pos = to_pos(i);
        if (pos.col > 0) {
            c = get(pos.col-1, pos.row);
            return true;
        } else {
            return false;
        }
    }

    bool get_right(size_t i, char& c) {
        auto pos = to_pos(i);
        if (pos.col < cols) {
            c = get(pos.col+1, pos.row);
            return true;
        } else {
            return false;
        }
    }

    bool get_up(size_t i, char& c) {
        auto pos = to_pos(i);
        if (pos.row > 0) {
            c = get(pos.col, pos.row-1);
            return true;
        } else {
            return false;
        }
    }

    bool get_down(size_t i, char& c) {
        auto pos = to_pos(i);
        if (pos.row < rows) {
            c = get(pos.col, pos.row+1);
            return true;
        } else {
            return false;
        }
    }

    int compute_distance(char src, char dest) {
        if (dest <= src  + 1) {
            return 1;
        } else {
            return 0;
        }
    }

    vector<size_t> get_adjacency(size_t i) {
        vector<size_t> adjacency(rows*cols);
        for (size_t k = 0; k < rows * cols; k++) {
            if (neighbors(i, k)) {
                adjacency[k] = compute_distance(data[i], data[k]);
            } else {
                adjacency[k] = 0;
            }
        }
        return adjacency;
    }

    char* data;
    size_t cols = 0;
    size_t rows = 0;
};

size_t pick_nearest_not_visited(const vector<size_t>& dist, const vector<bool>& visited)
{
    size_t min_dist = UINT_MAX;
    size_t nearest = 0;
    for (size_t i = 0; i < dist.size(); i++) {
        if (!visited[i] && dist[i] < min_dist) {
            min_dist = dist[i];
            nearest = i;
        }
    }
    return nearest;
}

size_t dijkstra(const vector<vector<size_t>>& graph, size_t src, size_t dest)
{
    vector<size_t> dist;
    vector<bool> visited;

    for (size_t i = 0; i < graph[0].size(); i++) {
        dist.push_back(UINT_MAX);
        visited.push_back(false);
    }

    dist[src] = 0;

    for (size_t count = 0; count < graph[0].size() - 1; count++) {
        size_t nearest = pick_nearest_not_visited(dist, visited);
        visited[nearest] = true;
        if (nearest == dest) {
            break;
        }
        for (size_t v = 0; v < graph[0].size(); v++) {
            if (!visited[v] && graph[nearest][v] > 0 
                && dist[nearest] != UINT_MAX
                && dist[nearest] + graph[nearest][v] < dist[v]) {
                    dist[v] = dist[nearest] + graph[nearest][v];
            }
        }
    }

    //aoc::print_collection(dist);

    return dist[dest];
}

ostream& operator<<(ostream& out, const World& world)
{
    for (size_t row = 0; row < world.rows; row++) {
        for (size_t col = 0; col < world.cols; col++) {
            cout << world.get(col, row);
        }
        cout << endl;
    }
    return out;
}

int main()
{
    World world;
    vector<size_t> sources;
    size_t src = 0;
    size_t dest = 0;
    size_t i = 0;
    ifstream f{"input.txt"};
    for (string line; getline(f, line); ) {
        world.cols = line.size();
        for (auto c : line) {
            if (c == 'S') {
                src = i;
                sources.push_back(i);
                world.data[i] = 'a';
            } else if (c == 'E') {
                dest = i;
                world.data[i] = 'z';
            } else {
                world.data[i] = c;
                if (c == 'a') {
                    sources.push_back(i);
                }
            }
            ++i;
        }
        ++world.rows;
    }

    vector<vector<size_t>> graph;

    for (size_t i = 0; i < world.rows * world.cols; i++) {
        auto result = world.get_adjacency(i);
        graph.push_back(result);
    }

    cout << "Found " << sources.size() << " as" << endl;
    size_t min_dist = UINT_MAX;
    for (auto s: sources) {
        auto dist = dijkstra(graph, s, dest);
        if (dist < min_dist) {
            min_dist = dist;
            cout << min_dist << endl;
        }
    }

    return 0;
}