from dijkstar import Graph, find_path

galaxy = Graph(undirected=True)

def part_two():
    with open("./input.txt") as input:
        for line in input.readlines():
            objs = line.strip().split(")")

            galaxy.add_edge(objs[0], objs[1], 1)

        path_info = find_path(galaxy, 'YOU', 'SAN')
        print(path_info.total_cost - 2)

if __name__ == "__main__":
    part_two()
