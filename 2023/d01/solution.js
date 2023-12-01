import { readInput } from "../utils.js";

const input = await readInput();
const start = performance.now();

const values = input.split("\n");

const getSum = (values) =>
  values
    .map((match) => match.join(""))
    .map((value) => +(value[0] + value[value.length - 1]))
    .reduce((acc, curr) => acc + curr, 0);

// part-1
const part1Solution = getSum(values.map((value) => value.match(/\d/g)));
console.log(part1Solution);

// part-2
const transform = {
  one: "1",
  two: "2",
  three: "3",
  four: "4",
  five: "5",
  six: "6",
  seven: "7",
  eight: "8",
  nine: "9",
};
const pattern = `\\d|${Object.keys(transform).join("|")}`;
const part2Solution = getSum(
  values.map((value) =>
    [...value.matchAll(`(?=(${pattern}))`)].map(
      (match) => transform[match[1]] ?? match[1],
    ),
  ),
);
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
