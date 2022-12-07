use std::fs;
use regex::Regex;
use std::collections::HashMap;

struct Prog {
    mask: String,
    memory: HashMap<u64, u64>
}

fn number_from_binary_string(value: &str) -> u64 {
    let mut result: u64 = 0;
    let mut weight = 2u64.pow(35);
    for c in value.chars() {
        if c == '1' {
            result += weight;
        }
        weight /= 2;
    
    }
    return result;
}

fn apply_mask(mask: &str, number: u64) -> u64 {
    let mut result_bits = Vec::<u8>::new();
    let number_binary_str = format!("{:0>36b}", number);
    for (i, m) in mask.chars().enumerate() {
        let bit: u8 = number_binary_str.chars().nth(i).map_or_else(|| 0, |c|
            match c {
                '1' => 1,
                _ => 0
        });
        if m == '0' {
            result_bits.push(0);
        } else if m == '1' {
            result_bits.push(1);
        } else {
            result_bits.push(bit);
        }
    }

    let mut result: u64 = 0;
    let mut weight = 2u64.pow(35);
    for b in result_bits {
        result += b as u64 * weight;
        weight /= 2;
    
    }
    return result;
}

fn parse_mask(line: &str) -> String {
    let items: Vec<&str> = line.split("=").collect();
    return String::from(items[1].trim());
}

fn get_floating_indexes(mask: &str) -> Vec<usize> {
    let mut indexes = Vec::new();
    for (i, c) in mask.chars().enumerate() {
        if c == 'X' {
            indexes.push(i);
        }
    }
    return indexes;
}

fn parse_write_memory(regex: &Regex, line: &str) -> (u64, u64) {
    let cap = regex.captures(line).unwrap();
    return (cap[1].parse::<u64>().unwrap(), cap[2].parse::<u64>().unwrap());
}

fn main() {
    println!("Hello, world!");

    let re = Regex::new(r"^mem\[(\d+)\] = (\d+)").unwrap();

    /*assert_eq!(apply_mask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X", 11), 73);
    assert_eq!(apply_mask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X", 101), 101);
    assert_eq!(apply_mask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X", 0), 64);*/
    assert_eq!(number_from_binary_string("000000000000000000000000000000101010"), 42);
    assert_eq!(parse_write_memory(&re, "mem[8] = 10"), (8, 10));
    assert_eq!(parse_mask("mask = 001X11X1X010X1X1010XX10X100101011000"), "001X11X1X010X1X1010XX10X100101011000");
    assert_eq!(get_floating_indexes("000000000000000000000000000000X1001X"), vec![30, 35]);

    let mut prog = Prog {
        mask: String::new(),
        memory: HashMap::new()
    };

    for line in fs::read_to_string("input_full.txt")
        .expect("Failed to read the file")
        .lines() {
            /*if line.starts_with("mask") {
                prog.mask = parse_mask(line);
            } else if line.starts_with("mem") {
                let m = parse_write_memory(&re, line);
                let value = apply_mask(&prog.mask, m.1);
                prog.memory.insert(m.0, value);
            }*/
        }
    //let result: u64 = prog.memory.values().sum();
    //println!("result {}", result);
}
