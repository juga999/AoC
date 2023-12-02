#include "../utils/utils.hpp"

typedef std::vector<std::string> str_vec_t;
typedef std::map<std::string, uint> reveal_t;

struct Game {
    uint game_nb = 0;
    uint max_red = 0;
    uint max_green = 0;
    uint max_blue = 0;
};

reveal_t parse_reveal(const std::string& input)
{
    reveal_t reveal = {{"red", 0}, {"green", 0}, {"blue", 0}};
    str_vec_t items;
    aoc::split(aoc::trim(input), ',', items);
    for (auto item : items) {
        str_vec_t colors;
        aoc::split(aoc::trim(item), ' ', colors);
        reveal[aoc::trim(colors[1])] = *aoc::to_int(colors[0]);
    }
    return reveal;
}

bool is_possible_game(const Game& game, uint reds, uint greens, uint blues)
{
    return game.max_red <= reds && game.max_green <= greens && game.max_blue <= blues;
}

uint get_game_power(const Game& game)
{
    return game.max_red * game.max_green * game.max_blue;
}

Game init_game(const std::string& line)
{
    Game game;

    str_vec_t data;
    aoc::split(line, ':', data);
    {
        str_vec_t v1;
        aoc::split(data[0], ' ', v1);
        game.game_nb = *aoc::to_int(v1[1]);
    }
    {
        str_vec_t v1;
        aoc::split(data[1], ';', v1);
        {
            for (auto reveal_input : v1) {
                auto reveal = parse_reveal(reveal_input);
                game.max_red = std::max(game.max_red, reveal["red"]);
                game.max_green = std::max(game.max_green, reveal["green"]);
                game.max_blue = std::max(game.max_blue, reveal["blue"]);
            }
        }
    }
    return game;
}

int main()
{
    uint sum = 0;
    uint power = 0;
    std::ifstream f{"input.txt"};
    for (std::string line; std::getline(f, line); ) {
        auto game = init_game(line);
        if (is_possible_game(game, 12, 13, 14)) {
            sum += game.game_nb;
        }
        auto p = get_game_power(game);
        power += p;
    }
    std::cout << "part 1: " << sum << std::endl;
    std::cout << "part 2: " << power << std::endl;

    return 0;
}
