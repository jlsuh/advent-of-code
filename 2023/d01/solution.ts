import { alts, ints, readInput } from "../utils.js";

const input = await readInput();
const start = performance.now();

const getSum = (values: string[][]) =>
  values
    .map((digits) => digits.join(""))
    .map((value) => +(value[0] + value[value.length - 1]))
    .reduce((acc, curr) => acc + curr, 0);

// part-1
const part1Solution = getSum(input.map((value) => ints(value)));
console.log(part1Solution);

// part-2
const transform: {
  [key: string]: string;
} = {
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
const pattern = `(?=(\\d|${alts(transform)}))`;
const part2Solution = getSum(
  input.map((value) =>
    [...value.matchAll(new RegExp(pattern, "g"))].map(
      (match) => transform[match[1]] ?? match[1],
    ),
  ),
);
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
