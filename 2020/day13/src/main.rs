use std::fs;

fn main() {
    let content = fs::read_to_string("input_full.txt")
        .expect("Failed to read the file");

    let mut lines_iter = content.lines();
    let timestamp = lines_iter.next().map(|l| l.parse::<u32>().unwrap()).unwrap();
    let schedule_line = lines_iter.next().unwrap();

    let ids: Vec<u32> = schedule_line.split(",")
        .filter(|e| e != &"x")
        .map(|e| e.parse::<u32>().unwrap())
        .collect();
    println!("{:?}", ids);

    let mut bus_id:u32 = 0;
    let mut min_wait:u32 = u32::MAX;
    let deltas: Vec<u32> = ids.iter()
        .map(|id| (timestamp - (timestamp % id)) + id)
        .map(|t| t - timestamp)
        .collect();
    println!("{:?}", deltas);
    for i in 0..deltas.len() {
        if deltas[i] < min_wait {
            min_wait = deltas[i];
            bus_id = ids[i];
        }
    }
    println!("{}", bus_id * min_wait);
}
