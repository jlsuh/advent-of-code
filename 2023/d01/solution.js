import { readInput } from "../utils.js";

const input = await readInput();
const start = performance.now();
const solution1 = input
  .split("\n")
  .map((value) => {
    const digits = value.match(/\d/g);
    return +(digits[0] + digits[digits.length - 1]);
  })
  .reduce((acc, curr) => acc + curr, 0);
console.log(solution1);

const transform = {
  one: 1,
  two: 2,
  three: 3,
  four: 4,
  five: 5,
  six: 6,
  seven: 7,
  eight: 8,
  nine: 9,
  oneight: 18,
  twone: 21,
  threeight: 38,
  fiveight: 58,
  sevenine: 79,
  eightwo: 82,
  eighthree: 83,
  nineight: 98,
};

const solution2 = input
  .split("\n")
  .map((value) => {
    const val = value.replaceAll(
      /oneight|twone|threeight|fiveight|sevenine|eightwo|eighthree|nineight|one|two|three|four|five|six|seven|eight|nine/g,
      (matched) => transform[matched],
    );
    return val;
  })
  .map((value) => {
    const digits = value.match(/\d/g);
    return +(digits[0] + digits[digits.length - 1]);
  })
  .reduce((acc, curr) => acc + curr, 0);
console.log(solution2);
console.log("Elapsed:", performance.now() - start);
