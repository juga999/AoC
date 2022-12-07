use std::fs;
use std::collections::HashMap;

fn main() {
    let mut counts: Vec<usize> = Vec::new();

    let mut persons = 0;
    let mut yes_frequency: HashMap<char, usize> = HashMap::new();
    for line in fs::read_to_string("input_full.txt")
        .expect("Failed to read the file")
        .lines() {
            if line.len() > 0 {
                persons += 1;
                for c in line.chars() {
                    *yes_frequency.entry(c).or_insert(0) += 1;
                }
            } else {
                let mut sub_total = 0;
                for (_, f) in &yes_frequency {
                    if *f == persons {
                        sub_total += 1;
                    }
                }
                counts.push(sub_total);
                yes_frequency.clear();
                persons = 0;
            }
        }

        let mut sub_total = 0;
        for (_, f) in &yes_frequency {
            if *f == persons {
                sub_total += 1;
            }
        }
        counts.push(sub_total);

    let total: usize = counts.iter().sum();
    println!("{}", total);
}
