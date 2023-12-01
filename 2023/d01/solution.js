import { readInput } from "../utils.js";

const input = await readInput();
const start = performance.now();

const values = input.split("\n");

const getSum = (values) =>
  values
    .map((value) => {
      const digits = value.match(/\d/g);
      return +(digits[0] + digits[digits.length - 1]);
    })
    .reduce((acc, curr) => acc + curr, 0);

// part-1
const s1 = getSum(values);
console.log(s1);

// part-2
const transform = {
  oneight: 18,
  twone: 21,
  threeight: 38,
  fiveight: 58,
  sevenine: 79,
  eightwo: 82,
  eighthree: 83,
  nineight: 98,
  one: 1,
  two: 2,
  three: 3,
  four: 4,
  five: 5,
  six: 6,
  seven: 7,
  eight: 8,
  nine: 9,
};

const s2 = getSum(
  values.map((value) =>
    value.replaceAll(
      new RegExp(Object.keys(transform).join("|"), "g"),
      (matched) => transform[matched],
    ),
  ),
);
console.log(s2);

console.log("Elapsed:", performance.now() - start);
