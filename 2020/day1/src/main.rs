use std::fs;

fn matches(tuple: (u32, u32, u32)) -> bool {
    return
        tuple.0 != tuple.1
        && tuple.0 != tuple.2
        && tuple.1 != tuple.2
        && tuple.0 + tuple.1 + tuple.2 == 2020;
}

fn main() {
    let mut numbers: Vec<u32> = fs::read_to_string("input.txt")
        .expect("Failed to read the file")
        .lines()
        .map(|line| line.parse::<u32>().unwrap())
        .collect();
    numbers.sort();

    let mut loops = 0;
    for i in &numbers {
        for j in &numbers {
            for k in &numbers {
                loops += 1;
                if matches((*i,*j, *k)) {
                    println!("Loops: {}", loops);
                    println!("{}", i * j * k);
                    return;
                }
            }
        }
    }
}
