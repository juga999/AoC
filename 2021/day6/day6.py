from datetime import datetime

start_time = datetime.now()
starting_fish = [int(i) for i in open("input.txt", "r").read().split(',')]
total = 0

# Counts how many fish have the same days left till spawn
current_states = {
    0: starting_fish.count(0),
    1: starting_fish.count(1),
    2: starting_fish.count(2),
    3: starting_fish.count(3),
    4: starting_fish.count(4),
    5: starting_fish.count(5),
    6: starting_fish.count(6),
    7: starting_fish.count(7),
    8: starting_fish.count(8)
}
next_states = {}

# Repeat once per 'day'
for i in range(256):

    # 
    next_states = {
        0: current_states[1],
        1: current_states[2],
        2: current_states[3],
        3: current_states[4],
        4: current_states[5],
        5: current_states[6],
        6: current_states[7],
        7: current_states[8],
        8: current_states[0]
    }

    # Reset adult fish at 0 to 6
    if current_states[0] > 0:
        next_states[6] += current_states[0]

    # Move value of new_states to current_states, reset value new_states
    current_states = next_states
    next_states = {}

# After all days completed, sum up the values of all states
for fish in current_states:
    total += current_states[fish]
    
print(total)
print(datetime.now() - start_time)


