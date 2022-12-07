use std::collections::HashSet;
use std::fs;
use ndarray::Array2;

fn chain_adapters(adapters: &HashSet<u16>, connections: &mut Array2<u64>, src: u16) {
    let mut next_index = 0;
    for i in src+1..src+4 {
        if adapters.contains(&i) {
            if next_index == 0 {
                next_index = i;
            }
            connections[[src as usize, i as usize]] = 1;
        }
    }
    if next_index > 0 {
        chain_adapters(adapters, connections, next_index);
    }  
}

fn main() {
    let mut possible_connections: Vec<Array2::<u64>> = Vec::new();
    let mut adapters: HashSet<u16> = HashSet::new();
    let mut max = 0;
    // input.txt -> max = 49
    // input_small.txt -> max = 19
    // input_full.txt -> max = 174
    for n in fs::read_to_string("input_full.txt")
        .expect("Failed to read the file")
        .lines()
        .map(|line| line.parse::<u16>().unwrap()) {
            if n > max {
                max = n;
            }
            adapters.insert(n);
            
        }

    adapters.insert(0);
    println!("max {}", max);

    let mut connections = Array2::<u64>::zeros((175, 175));
    chain_adapters(&adapters, &mut connections, 0);

    possible_connections.push(connections.clone());
    for i in 0..adapters.len() {
        possible_connections.push(possible_connections[i].dot(&connections));
    }
    let count: u64 = possible_connections.iter().map(|pc| pc[[0,174]]).sum();
    println!("result: {}", count);
}
