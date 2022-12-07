use std::fs;

struct PasswordWithPolicy {
    c: char,
    min_occ: usize,
    max_occ: usize,
    password: String
}

impl PasswordWithPolicy {
    fn new(text: &str) -> PasswordWithPolicy {
        let values: Vec<&str> = text.split(":").collect();
        let policy_and_char: Vec<&str> = values[0].split(' ').collect();
        let policy_values: Vec<usize> = policy_and_char[0].split('-')
            .map(|v| v.parse::<usize>().unwrap())
            .collect();
    
        return PasswordWithPolicy {
            c: policy_and_char[1].chars().next().unwrap(),
            min_occ: policy_values[0],
            max_occ: policy_values[1],
            password: (*values[1].trim()).to_string()
        };        
    }

    fn is_password_valid(&self) -> bool {
        //let allowed_indices = vec![self.min_occ, self.max_occ];
        let count = self.password.match_indices(self.c)
            .filter(|t| t.0 + 1 == self.min_occ || t.0 + 1 == self.max_occ)
            //.filter(|t| allowed_indices.contains(&(t.0 + 1)))
            .count();
        return count == 1;
    }
}

fn main() {
    let count = fs::read_to_string("input.txt")
        .expect("Failed to read the file")
        .lines()
        .map(|line| PasswordWithPolicy::new(&line.to_string()))
        .filter(|p| p.is_password_valid())
        .count();
    println!("result: {}", count); // 673
}
