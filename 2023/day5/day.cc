#include "../utils/utils.hpp"

typedef std::vector<std::string> str_vec_t;

struct AlmanacRange
{
    uint start;
    uint len;

    uint end() const {
        return start + len -1;
    }

    void print(bool endline=true) const {
        std::cout << "[" << start << "->" <<  end() << "] (" << len << ")";
        if (endline) {
            std::cout << std::endl;
        } 
    }
};

namespace std
{
    template<> struct less<AlmanacRange>
    {
       bool operator() (const AlmanacRange& lhs, const AlmanacRange& rhs) const
       {
            if (lhs.start == rhs.start) {
                return lhs.len < rhs.len;
            } else {
                return lhs.start < rhs.start;
            }
       }
    };
}

struct AlmanacEntry
{
    std::map<AlmanacRange, AlmanacRange> range_mapping;
    std::map<AlmanacRange, AlmanacRange> rev_range_mapping;

    uint get_dest(uint src) {
        for (auto&& range : range_mapping | rv::keys) {
            if (src >= range.start && src < range.start + range.len) {
                auto&& dest = range_mapping[range];
                return dest.start + (src - range.start);
            }
        }
        return src;
    }

    uint get_src(uint src) {
        for (auto&& range : rev_range_mapping | rv::keys) {
            if (src >= range.start && src < range.start + range.len) {
                auto&& dest = range_mapping[range];
                return dest.start + (src - range.start);
            }
        }
        return src;
    }
};

struct Almanac
{
    std::vector<uint> seeds;
    std::vector<AlmanacRange> seeds_ranges;
    std::vector<AlmanacEntry> entries;

    uint get_dest(uint src) {
        uint tmp_src = src;
        for (auto&& entry : entries) {
            tmp_src = entry.get_dest(tmp_src);
        }
        return tmp_src;
    }

    uint get_src(uint dest) {
        uint tmp_dest = dest;
        for (auto iter = std::make_reverse_iterator(entries.end()); iter != std::make_reverse_iterator(entries.begin()); ++iter) {
            tmp_dest = iter->get_src(tmp_dest);
        }
        return tmp_dest;
    }
};

void init_range(const std::string& line, AlmanacRange& from_range, AlmanacRange& to_range)
{
    std::vector<uint> values = *aoc::to_uints(line);
    from_range.start = values[1];
    from_range.len = values[2];
    to_range.start = values[0];
    to_range.len = values[2];
}

int main()
{
    Almanac almanac;
    std::vector<AlmanacEntry> entries;
    std::ifstream f{"input_test.txt"};
    for (std::string line; std::getline(f, line); ) {
        if (aoc::starts_with(line, "seeds:")) {
            str_vec_t data;
            aoc::split(line, ':', data);
            almanac.seeds = *aoc::to_uints(data[1]);
            for (size_t i=0; i < almanac.seeds.size(); i += 2) {
                AlmanacRange range;
                range.start = almanac.seeds[i];
                range.len = almanac.seeds[i+1];
                almanac.seeds_ranges.push_back(range);
            }
        } else if (line.rfind("map:") != std::string::npos) {
            AlmanacEntry entry;
            bool has_data = true;
            while (has_data && std::getline(f, line)) {
                has_data = line.length() > 1;
                if (has_data) {
                    AlmanacRange from_range;
                    AlmanacRange to_range;
                    init_range(line, from_range, to_range);
                    entry.range_mapping[from_range] = to_range;
                    entry.rev_range_mapping[to_range] = from_range;
                }
            }
            almanac.entries.push_back(entry);
        }
    }

    {
        uint min_location = UINT_MAX;
        for (auto seed : almanac.seeds) {
            uint location = almanac.get_dest(seed);
            if (location < min_location) {
                min_location = location;
            }
        }
        std::cout << min_location << std::endl << std::endl;// 35 / 218513636
    }

    {
        uint location = almanac.get_src(46);
        std::cout << location << std::endl;
    }
    return 0;
}
