use std::fs;
use ndarray::Array2;

#[derive(Debug,Clone,Copy,PartialEq)]
enum PlaceState {
    FLOOR,
    EMPTY,
    OCCUPIED
}

fn look_direction(row: i32, col: i32, room: &Array2<PlaceState>, row_dir: i32, col_dir: i32) -> PlaceState{
    let mut row_offset: i32 = 1;
    let mut col_offset: i32 = 1;
    loop {
        let next_row = row + (row_offset * row_dir);
        if next_row < 0 || next_row >= room.nrows() as i32 {
            break;
        }
        let next_col = col + (col_offset * col_dir);
        if next_col < 0 || next_col >= room.ncols() as i32 {
            break;
        }
        let state = room[[next_row as usize, next_col as usize]];
        if state != PlaceState::FLOOR {
            return state;
        }
        row_offset += 1;
        col_offset += 1;
    }

    return PlaceState::FLOOR;
}

fn switch_seat(row: i32, col: i32, room: &Array2<PlaceState>, new_room: &mut Array2<PlaceState>) {
    let mut states: Vec<PlaceState> = Vec::new();
    states.push(look_direction(row, col, room, -1, 0)); // north
    states.push(look_direction(row, col, room, -1, 1)); // north-east
    states.push(look_direction(row, col, room, 0, 1)); // east
    states.push(look_direction(row, col, room, 1, 1)); // south-east
    states.push(look_direction(row, col, room, 1, 0)); // south
    states.push(look_direction(row, col, room, 1, -1)); // south-west
    states.push(look_direction(row, col, room, 0, -1)); // west
    states.push(look_direction(row, col, room, -1, -1)); // north-west

    let occupied_count = states.iter().filter(|s| **s == PlaceState::OCCUPIED).count();

    let place_state = &room[[row as usize, col as usize]];
    if *place_state == PlaceState::EMPTY && occupied_count == 0 {
        new_room[[row as usize, col as usize]] = PlaceState::OCCUPIED;
    } else if *place_state == PlaceState::OCCUPIED && occupied_count >= 5 {
        new_room[[row as usize, col as usize]] = PlaceState::EMPTY;
    } else {
        new_room[[row as usize, col as usize]] = room[[row as usize, col as usize]];
    }
}

fn switch_seats<'a>(room: &'a mut Array2<PlaceState>, new_room: &'a mut Array2<PlaceState>) -> (&'a mut Array2<PlaceState>, &'a mut Array2<PlaceState>) {
    let rows = room.nrows();
    let cols = room.ncols();

    for r in 0..rows {
        for c in 0..cols {
            switch_seat(r as i32, c as i32, room, new_room);
        }
    }

    return (new_room, room);
}

fn count_states(room: &Array2<PlaceState>, state: PlaceState) -> usize {
    let rows = room.nrows();
    let cols = room.ncols();

    let mut count = 0;
    for r in 0..rows {
        for c in 0..cols {
            if room[[r, c]] == state {
                count += 1;
            }
        }
    }
    return count;
}

fn main() {
    println!("Hello, world!");

    //let rows: usize = 10;
    let rows: usize = 90;
    //let cols: usize = 10;
    let cols: usize = 95;

    let mut room1: Array2<PlaceState> = Array2::<PlaceState>::from_elem((rows, cols), PlaceState::FLOOR);
    let mut room2: Array2<PlaceState> = room1.clone();

    let mut row: usize = 0;
    let mut col: usize;
    for line in fs::read_to_string("input_full.txt")
        .expect("Failed to read the file")
        .lines() {
            col = 0;
            for c in line.chars() {
                match c {
                    'L' => room1[[row, col]] = PlaceState::EMPTY,
                    '#' => room1[[row, col]] = PlaceState::OCCUPIED,
                    _ => room1[[row, col]] = PlaceState::FLOOR,
                }
                col += 1;
            }
            row += 1;
        }

    let mut rooms = switch_seats(&mut room1, &mut room2);
    loop {
        if rooms.0 == rooms.1 {
            break;
        }
        rooms = switch_seats(rooms.0, rooms.1);
    }
    //println!("{:?}", rooms.0);
    println!("result {}", count_states(rooms.0, PlaceState::OCCUPIED))
}
