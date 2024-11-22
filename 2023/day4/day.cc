#include "../utils/utils.hpp"

typedef std::vector<std::string> str_vec_t;

struct Card {
    uint id;
    std::set<uint> numbers;
    std::set<uint> winning_numbers;
    std::vector<uint> matching_numbers;
    uint next_matching_ids;
    uint score;
};

Card init_card(const std::string& line)
{
    Card card;

    str_vec_t data;
    aoc::split(line, ':', data);

    auto [label, id] = *aoc::labelled_uint(data[0]);
    card.id = id;

    str_vec_t numbers_data;
    aoc::split(data[1], '|', numbers_data);

    aoc::to_uints(numbers_data[0])
        .map([&](auto&& v) { card.numbers = {v.begin(), v.end()}; });
    aoc::to_uints(numbers_data[1])
        .map([&](auto&& v) { card.winning_numbers = {v.begin(), v.end()}; });

    std::set_intersection(
        card.numbers.begin(), card.numbers.end(),
        card.winning_numbers.begin(), card.winning_numbers.end(),
        std::back_inserter(card.matching_numbers));
    if (card.matching_numbers.size() > 0) {
        card.score = 1 << (card.matching_numbers.size() - 1);
    } else {
        card.score = 0;
    }
    card.next_matching_ids = card.matching_numbers.size();

    return card;
}

int main()
{
    std::vector<Card> cards;
    std::ifstream f{"input.txt"};
    for (std::string line; std::getline(f, line); ) {
        Card card = init_card(line);
        cards.push_back(card);
    }
    uint total = 0;
    for (auto card : cards) {
        total += card.score;
    }
    std::cout << "part 1: " << total << std::endl; // 20117

    std::map<uint, uint> matching_map;
    for (const Card& card : cards) {
        matching_map[card.id] += 1;
        for (uint j = 0; j < card.next_matching_ids; j++) {
            // had to look it up :/
            matching_map[card.id + j + 1] += matching_map[card.id];
        }
    }
    auto values = matching_map | rv::values;
    uint sum = std::accumulate(values.begin(), values.end(), 0);
    std::cout << "part 2: " << sum << std::endl; // 13768818

    return 0;
}
