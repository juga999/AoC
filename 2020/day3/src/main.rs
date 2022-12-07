use std::fs;
use array2d::Array2D;

fn count_trees(pattern: &Array2D<char>, slope: &(usize, usize)) -> usize {
    let mut tree_count = 0;
    let mut pos = (0,0);
    while pos.0 < pattern.num_rows() {
        if pattern.get(pos.0, pos.1 % pattern.num_columns()) == Some(&'#') {
            tree_count += 1;
        }
        pos.0 += slope.0;
        pos.1 += slope.1;
    }

    return tree_count;
}

fn main() {
    let pattern_cols = 31;
    let pattern_rows = 323;
    let mut pattern = Array2D::filled_with(' ', pattern_rows, pattern_cols);

    for (row, line) in fs::read_to_string("input.txt")
        .expect("Failed to read the file")
        .lines()
        .enumerate() {
            for (col, c) in line.trim().chars().enumerate() {
                pattern.set(row, col, c).unwrap();
            }
        }
    assert_eq!(pattern.get(0, 4), Some(&'#'));
    assert_eq!(pattern.get(4, 1), Some(&'.'));
    assert_eq!(pattern.get(0, pattern_cols-1), Some(&'.'));
    assert_eq!(pattern.get(pattern_rows-1, pattern_cols-1), Some(&'.'));

    let result = vec![(1, 1), (1, 3), (1, 5), (1, 7), (2, 1)].iter()
        .map(|slope| count_trees(&pattern, slope))
        .fold(1, |acc, n| acc * n);

    println!("Result: {}", result);
    assert_eq!(result, 2106818610);
}
