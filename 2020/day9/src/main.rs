use std::collections::HashSet;
use std::fs;

fn is_number_valid(n: u64, seq: &[u64]) -> Option<(u64,u64)> {
    let mut numbers: HashSet<u64> = HashSet::new();
    for v in seq {
        numbers.insert(*v);
    }

    for candidate in &numbers {
        if n > *candidate && 2 * (*candidate) != n {
            let other_number = numbers.get(&(n - (*candidate)));
            if other_number != None {
                return Some((*candidate, *other_number.unwrap()));
            }
        }
    }
    return None;
}

fn main() {
    {
        let result = is_number_valid(26, &(1..26).collect::<Vec<u64>>());
        assert_eq!(result.unwrap_or((0,0)) != (0,0), true);
    }
    {
        let result = is_number_valid(49, &(1..26).collect::<Vec<u64>>());
        assert_eq!(result.unwrap_or((0,0)) != (0,0), true);
    }
    {
        let result = is_number_valid(50, &(1..26).collect::<Vec<u64>>());
        assert_eq!(result.unwrap_or((0,0)) == (0,0), true);
    }

    let expected_value = 1504371145;
    let mut start: usize = 0;
    let mut offset: usize = 2;
    let numbers: Vec<u64> = fs::read_to_string("input_full.txt")
        .expect("Failed to read the file")
        .lines()
        .map(|line| line.parse::<u64>().unwrap())
        .collect();
    
    loop {
        if start >= numbers.len() {
            break;
        }
        loop {
            if start + offset >= numbers.len() {
                break;
            }
            let sum: u64 = numbers[start..start+offset].iter().sum();
            if sum == expected_value {
                println!("Found it");
                println!("{:?} - {}", &numbers[start..start+offset], sum);
                let min = numbers[start..start+offset].iter().min().unwrap();
                let max = numbers[start..start+offset].iter().max().unwrap();
                println!("{} + {} : {}", min, max, min + max);
                break;
            } else if sum > expected_value {
                offset = 2;
                break;
            } else {
                offset += 1;
            }
        }
        start += 1;
    }
}
