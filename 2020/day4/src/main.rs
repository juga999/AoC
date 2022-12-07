use std::fs;
use std::collections::HashMap;
use std::collections::HashSet;
use regex::Regex;

fn parse_year_or_0(value: &String) -> u32 {
    if value.chars().count() != 4 {
        return 0;
    }
    return match value.parse::<u32>() {
        Ok(year) => year,
        Err(_) => 0
    };
}

fn is_byr_valid(value: &String) -> bool {
    let year = parse_year_or_0(value);
    return year >= 1920 && year <= 2002;
}

fn is_iyr_valid(value: &String) -> bool {
    let year = parse_year_or_0(value);
    return year >= 2010 && year <= 2020;
}

fn is_eyr_valid(value: &String) -> bool {
    let year = parse_year_or_0(value);
    return year >= 2020 && year <= 2030; 
}

fn is_hgt_valid(value: &String) -> bool {
    let mut is_inches = false;
    let mut unit_index = value.rfind("in").unwrap_or(0);
    if unit_index > 0 {
        is_inches = true;
    } else {
        unit_index = value.rfind("cm").unwrap_or(0);
    }
    if unit_index > 0 {
        let mut tmp_value = value.clone();
        let _ = tmp_value.split_off(unit_index);
        let height = match tmp_value.parse::<u32>() {
            Ok(v) => v,
            Err(_) => 0
        };
        if is_inches && height >= 59 && height <= 76 {
            return true;
        } else if !is_inches && height >= 150 && height <= 193 {
            return true;
        }
    }
    return false;
}

fn is_hcl_valid(value: &String) -> bool {
    let hair_color_regex = Regex::new(r"^#[0-9a-f]{6}$").unwrap();
    if hair_color_regex.is_match(value) {
        return true;
    }
    return false;
}

fn is_ecl_valid(value: &String) -> bool {
    let mut allowed = HashSet::new();
    allowed.insert("amb".to_string());
    allowed.insert("blu".to_string());
    allowed.insert("brn".to_string());
    allowed.insert("gry".to_string());
    allowed.insert("grn".to_string());
    allowed.insert("hzl".to_string());
    allowed.insert("oth".to_string());
    if allowed.contains(value) {
        return true;
    }
    return false;
}

fn is_pid_valid(value: &String) -> bool {
    let pid_regex = Regex::new(r"^\d+$").unwrap();
    let id = value.trim_start_matches('0');
    if pid_regex.is_match(id) && value.chars().count() == 9 {
        return true;
    }
    return false;
}

fn main() {
    let valid_fields = vec!["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"];
    let mut passport = HashMap::new();
    let mut count = 0;
    for line in fs::read_to_string("input_full.txt")
        .expect("Failed to read the file")
        .lines() {
            let fields: Vec<&str> = line.split_ascii_whitespace().collect();
            if fields.len() > 0 {
                for field in fields {
                    let key_and_value: Vec<&str> = field.split(':').collect();
                    assert_eq!(true, valid_fields.contains(&key_and_value[0]));
                    let key = String::from(key_and_value[0]);
                    let value = String::from(key_and_value[1]);
                    if key == "cid" {
                        continue;
                    }
                    if key == "byr" && !is_byr_valid(&value) {
                        continue;
                    }
                    if key == "iyr" && !is_iyr_valid(&value) {
                        continue;
                    }
                    if key == "eyr" && !is_eyr_valid(&value) {
                        continue;
                    }
                    if key == "hgt" && !is_hgt_valid(&value) {
                        continue;
                    }
                    if key == "hcl" && !is_hcl_valid(&value) {
                        continue;
                    }
                    if key == "ecl" && !is_ecl_valid(&value) {
                        continue;
                    }
                    if key == "pid" && !is_pid_valid(&value) {
                        continue;
                    }
                    passport.insert(key, value);
                }
            } else {
                if passport.len() == 7 {
                    count += 1;
                }
                passport.clear();
            }
        }
        if passport.len() == 7 {
            count += 1;
        }
    println!("Result = {}", count); // 188
}
