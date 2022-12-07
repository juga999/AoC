use std::fs;

#[derive(Debug,PartialEq,Clone)]
enum Operation {
    ACC,
    JMP,
    NOP
}

#[derive(Debug,PartialEq,Clone)]
struct Instruction {
    operation: Operation,
    arg: i32,
    executed: bool
}

fn parse_instruction(line: &str) -> Instruction {
    let mut items = line.split_ascii_whitespace();
    let operation = match items.next().unwrap() {
        "acc" => Operation::ACC,
        "jmp" => Operation::JMP,
        "nop" => Operation::NOP,
        _ => Operation::NOP
    };
    let arg: i32 = items.next().unwrap().parse().unwrap();
    return Instruction {
        operation: operation,
        arg: arg,
        executed: false
    };
}

fn run_program(program: &mut Vec<Instruction>) -> (bool, i32) {
    let mut valid = false;
    let mut accumulator: i32 = 0;
    let mut index: i32 = 0;
    loop {
        if index >= program.len() as i32 {
            valid = true;
            break;
        }
        let instruction = &mut program [index as usize];
        //println!("{:?}", instruction);
        if instruction.executed == true {
            break;
        }
        match instruction.operation {
            Operation::NOP => {
                index += 1;
            },
            Operation::JMP => {
                index += instruction.arg;
            },
            Operation::ACC => {
                accumulator += instruction.arg;
                index += 1;
            }
        };
        instruction.executed = true;
    }
    return (valid, accumulator);
}

fn fix_program(program: &Vec<Instruction>, index: usize, operation: Operation) -> Vec<Instruction> {
    let mut p = program.to_vec();
    if p[index].operation == Operation::JMP && operation == Operation::NOP {
        p[index].operation = operation;

    } else if p[index].operation == Operation::NOP && p[index].arg != 0 && operation == Operation::JMP {
        p[index].operation = operation;
    }
    return p;
}

fn main() {
    println!("Hello, world!");

    {
        let parsed = parse_instruction("nop +0");
        assert_eq!(parsed.operation, Operation::NOP);
        assert_eq!(parsed.arg, 0);
    }
    {
        let parsed = parse_instruction("jmp +4");
        assert_eq!(parsed.operation, Operation::JMP);
        assert_eq!(parsed.arg, 4);
    }
    {
        let parsed = parse_instruction("acc -99");
        assert_eq!(parsed.operation, Operation::ACC);
        assert_eq!(parsed.arg, -99);
    }

    let program: Vec<Instruction> = fs::read_to_string("input_full.txt")
        .expect("Failed to read the file")
        .lines()
        .map(|l| parse_instruction(l))
        .collect();

    for (i, _) in program.iter().enumerate() {
        let (valid, result) = run_program(&mut fix_program(&program, i, Operation::NOP));
        if valid {
            println!("with NOP result {}", result);
            break;
        }
    }
    for (i, _) in program.iter().enumerate() {
        let (valid, result) = run_program(&mut fix_program(&program, i, Operation::JMP));
        if valid {
            println!("with JMP result {}", result);
            break;
        }
    }
}
