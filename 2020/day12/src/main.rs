use std::fs;

#[derive(Debug,Clone,Copy,PartialEq)]
struct Waypoint {
    row: i32,
    col: i32
}

#[derive(Debug,Clone,Copy,PartialEq)]
struct Ship {
    waypoint: Waypoint,
    row: i32,
    col: i32
}

#[derive(Debug,Clone,Copy,PartialEq)]
struct Instruction {
    action: char,
    value: i32
}

fn rotate_waypoint_clockwise(waypoint: &mut Waypoint, degrees: i32) {
    let mut d = 0;
    loop {
        if d >= degrees {
            break;
        }
        let original = (waypoint.row, waypoint.col);
        // (r -1, c 0) -> (r 0, c 1)
        waypoint.row = original.1;
        waypoint.col = original.0 * -1;

        d += 90;
    }
}

fn rotate_waypoint_counterclockwise(waypoint: &mut Waypoint, degrees: i32) {
    let mut d = 0;
    loop {
        if d >= degrees {
            break;
        }
        let original = (waypoint.row, waypoint.col);
        // (r 0, c -1) -> (r 1, c 0)
        waypoint.row = original.1 * -1;
        waypoint.col = original.0;

        d += 90;
    }
}

fn move_ship_to_waypoint(ship: &mut Ship, count: i32) {
    ship.row += count * ship.waypoint.row;
    ship.col += count * ship.waypoint.col;
}

fn move_ship(ship: &mut Ship, instruction: &Instruction) {
    match instruction.action {
        'N' => {
            ship.waypoint.row -= instruction.value
        },
        'S' => {
            ship.waypoint.row += instruction.value
        },
        'E' => {
            ship.waypoint.col += instruction.value
        },
        'W' => {
            ship.waypoint.col -= instruction.value
        },
        'R' => {
            rotate_waypoint_clockwise(&mut ship.waypoint, instruction.value)
        },
        'L' => {
            rotate_waypoint_counterclockwise(&mut ship.waypoint, instruction.value)
        },
        'F' => {
            move_ship_to_waypoint(ship, instruction.value)
        }
        _ => {}
    }
}

fn parse_instruction(line: &str) -> Instruction {
    let action = line.chars().next().unwrap();
    let value = line.get(1..).unwrap().parse::<i32>().unwrap();

    return Instruction {
        action: action,
        value: value
    }
}

fn main() {
    let mut ship = Ship {
        waypoint: Waypoint {
            row: -1,
            col: 10
        },
        row: 0,
        col: 0
    };

    for line in fs::read_to_string("input_full.txt")
        .expect("Failed to read the file")
        .lines() {
            let instruction = parse_instruction(line);
            move_ship(&mut ship, &instruction);
        }
    println!("{:?}", ship);
    println!("result : {}", ship.row.abs() + ship.col.abs());
}
