use std::hash::{Hash, Hasher};
use std::fs;
use std::collections::HashMap;
use std::collections::HashSet;
//use petgraph::dot::Dot;
//use petgraph::graphmap::DiGraphMap;

#[derive(Debug)]
struct BagRule {
    name: String,
    content: HashMap<String, usize>
}

impl BagRule {
    fn new(name: &str) -> BagRule {
        return BagRule {
            name: name.to_string(),
            content: HashMap::new()
        }
    }

    fn count_bags(self: &BagRule, rules: &HashSet<BagRule>) -> usize {
        if self.content.len() == 0 {
            return 0;
        }
        let mut total: usize = self.content.values().sum();
        for (name, count) in self.content.iter() {
            total += count * rules.get(&BagRule::new(name)).unwrap().count_bags(rules);
        }
        return total;
    }
}

impl PartialEq for BagRule {
    fn eq(&self, other: &Self) -> bool {
        return self.name == other.name;
    }
}
impl Eq for BagRule {}

impl Hash for BagRule {
    fn hash<H: Hasher>(&self, state: &mut H) {
        return self.name.hash(state);
    }
}

fn parse_item(item_str: &str) -> (String, usize) {
    let mut v: Vec<&str> = item_str.split(' ').collect();
    if v.len() == 0 || v[0] == "no" {
        return ("".to_string(), 0);
    }
    v.pop();
    let count = v[0].parse::<usize>().unwrap();
    let name = v[1..v.len()].join(" ");
    return (name, count);
}

fn parse_items(items_str: &str) -> HashMap<String, usize> {
    let mut content = HashMap::new();
    for item_str in items_str.split(',') {
        let item = parse_item(item_str.trim());
        if item.1 > 0 {
            content.insert(item.0, item.1);
        }
    }
    return content;
}

fn parse_rule(rule: &str) -> BagRule {
    let v: Vec<&str> = rule.split("contain").collect();
    return BagRule {
        name: String::from(v[0].replace("bags", "").trim()),
        content: parse_items(v[1])
    }
}

fn main() {
    {
        let parsed = parse_item("1 bright white bag.");
        assert_eq!(parsed.0, "bright white");
        assert_eq!(parsed.1, 1);
    }
    {
        let parsed = parse_item("no other bags.");
        assert_eq!(parsed.0, "");
        assert_eq!(parsed.1, 0);
    }
    {
        let parsed = parse_items("1 bright white bag, 2 muted yellow bags.");
        assert_eq!(parsed.get("bright white"), Some(&1));
        assert_eq!(parsed.get("muted yellow"), Some(&2));
    }
    {
        let parsed = parse_rule("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.");
        assert_eq!(parsed.name, "muted yellow");
        assert_eq!(parsed.content.get("shiny gold"), Some(&2));
        assert_eq!(parsed.content.get("faded blue"), Some(&9));
    }

    let mut bag_rules: HashSet<BagRule> = HashSet::new();
    for line in fs::read_to_string("input_full.txt")
        .expect("Failed to read the file")
        .lines() {
            let rule = parse_rule(line);
            bag_rules.insert(rule);
        }
    
    let shiny_gold_bag = bag_rules.get(&BagRule::new("shiny gold")).unwrap();
    let result = shiny_gold_bag.count_bags(&bag_rules);
    println!("total {}", result);
}
