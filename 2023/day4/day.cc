#include "../utils/utils.hpp"

typedef std::vector<std::string> str_vec_t;

struct Card {
    uint id;
    std::set<uint> numbers;
    std::set<uint> winning_numbers;
    std::vector<uint> matching_numbers;
    std::vector<uint> next_matching_ids;
    uint score;
};

Card init_card(const std::string& line)
{
    Card card;

    str_vec_t data;
    aoc::split(line, ':', data);

    aoc::split(aoc::trim(data[0]), ' ', [&](auto str, auto pos) {
        aoc::to_int(aoc::trim(str)).map([&card](auto value) {
            card.id = value;
        });
    });

    str_vec_t numbers_data;
    aoc::split(data[1], '|', numbers_data);

    aoc::split(aoc::trim(numbers_data[0]), ' ', [&](auto str, auto pos) {
        aoc::to_int(aoc::trim(str)).map([&card](auto value) {
            card.numbers.insert(value);
        });
    });
    aoc::split(aoc::trim(numbers_data[1]), ' ', [&](auto str, auto pos) {
        aoc::to_int(aoc::trim(str)).map([&card](auto value) {
            card.winning_numbers.insert(value);
        });
    });

    std::set_intersection(
        card.numbers.begin(), card.numbers.end(),
        card.winning_numbers.begin(), card.winning_numbers.end(),
        std::back_inserter(card.matching_numbers));
    if (card.matching_numbers.size() > 0) {
        card.score = 1;
        for (size_t i = 1; i < card.matching_numbers.size(); i++) {
            card.score *= 2;
        }
        for (size_t i = 0; i < card.matching_numbers.size(); i++) {
            card.next_matching_ids.push_back(card.id + i + 1);
        }
    } else {
        card.score = 0;
    }

    return card;
}

void part1()
{
    std::vector<Card> cards;
    std::ifstream f{"input_test.txt"};
    for (std::string line; std::getline(f, line); ) {
        Card card = init_card(line);
        cards.push_back(card);
    }
    uint total = 0;
    for (auto card : cards) {
        total += card.score;
    }
    std::cout << total << std::endl;
}

void add_card_ids(const std::vector<Card>& cards, uint id, std::vector<uint>& matching_ids)
{
    const Card& card = cards[id-1];
    matching_ids.insert(matching_ids.end(), card.next_matching_ids.begin(), card.next_matching_ids.end());
    for (uint next_id : card.next_matching_ids) {
        add_card_ids(cards, next_id, matching_ids);

    }
}

void part2()
{
    std::vector<Card> cards;
    std::ifstream f{"input.txt"};
    for (std::string line; std::getline(f, line); ) {
        Card card = init_card(line);
        cards.push_back(card);
    }

    std::vector<uint> matching_ids;
    for (const Card& card : cards) {
        matching_ids.push_back(card.id);
        if (card.next_matching_ids.size() > 0) {
            add_card_ids(cards, card.id, matching_ids);
        }
    }

    std::cout << matching_ids.size() << std::endl;
}

int main()
{
    part2(); // 13768818

    return 0;
}
