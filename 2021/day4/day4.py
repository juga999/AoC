import re

def build_board(f):
    board = []
    for i in range(5):
        values = re.split(r'\s+', f.readline().strip())
        row = list(map(lambda l: int(l.strip()), values))
        board.append(row)
    return board

def mark(board, number):
    for r in range(5):
        for c in range(5):
            if board[r][c] == number:
                board[r][c] = -1

def check_board(board):
    for r in range(5):
        res = all(n == -1 for n in board[r])
        if res is True:
            return True

    rotation = list(zip(*board[::-1]))
    for r in range(5):
        res = all(n == -1 for n in rotation[r])
        if res is True:
            return True

    return False

def sum_unmarked(board):
    result = 0
    for r in range(5):
        s = sum(filter(lambda n: n != -1, board[r]))
        result = result + s
    return result

def part1():
    numbers = []
    boards = []
    with open("input.txt") as f:
        numbers = list(map(lambda l: int(l.strip()), f.readline().split(",")))

        for index, line in enumerate(f):
            boards.append(build_board(f))
    
    found = False
    for n in numbers:
        if found is True:
            break
        for board in boards:
            mark(board, n)
            res = check_board(board)
            if res is True:
                s = sum_unmarked(board)
                print(s * n)
                found = True
                break

def part2():
    numbers = []
    boards = []
    with open("input.txt") as f:
        numbers = list(map(lambda l: int(l.strip()), f.readline().split(",")))

        for index, line in enumerate(f):
            boards.append(build_board(f))
    
    winning_boards = []
    for n in numbers:
        for index, board in enumerate(boards):
            if index not in winning_boards:
                mark(board, n)
                if check_board(board) is True:
                    winning_boards.append(index)
                    if len(winning_boards) == len(boards):
                        print(sum_unmarked(board) * n)


if __name__ == "__main__":
    part2()