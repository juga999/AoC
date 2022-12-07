use std::fs;

fn decode_seat_id(value: &str) -> usize {
    let rows_cols = value.split_at(7);
    let rows = rows_cols.0;
    let cols = rows_cols.1;

    let mut min: usize = 0;
    let mut max: usize = 127;
    for c in rows.chars() {
        let half: usize = (max - min) / 2;
        if c == 'F' {
            max = max - half - 1;
        } else {
            min = min + half + 1;
        }
    }
    let row = min;

    min = 0;
    max = 7;
    for c in cols.chars() {
        let half: usize = (max - min) / 2;
        if c == 'L' {
            max = max - half - 1;
        } else {
            min = min + half + 1;
        }
    }
    let col = min;
    
    return row * 8 + col;
}

fn main() {
    let mut seats: Vec<bool> = Vec::new();
    seats.resize_with(128*8, || false);

    let ids: Vec<usize> = fs::read_to_string("input.txt")
        .expect("Failed to read the file")
        .lines()
        .map(|l| decode_seat_id(l))
        .collect();
    for id in ids {
        seats[id] = true;
    }

    for i in 1..seats.len()-1 {
        if !seats[i] && seats[i-1] && seats[i+1] {
            println!("{}", i);
        }
    }
}