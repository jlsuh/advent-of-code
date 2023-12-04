import { ints, readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const schema = input.split("\r\n");

const nonCoaxialSteps = (row, col, numberOfRows) => {
  const steps = [];
  if (row - 1 >= 0) {
    steps.push({ row: row - 1, col });
  }
  if (row + 1 <= numberOfRows - 1) {
    steps.push({ row: row + 1, col });
  }
  return steps;
};

const adjacentSteps = ({ row, col }, content, nRows, rowLen) => {
  const low = col;
  const high = col + content.length - 1;
  const steps = [];
  if (low - 1 >= 0) {
    steps.push({ row, col: low - 1 }); // West
    steps.push(...nonCoaxialSteps(row, low - 1, nRows)); // West diagonals
  }
  if (high + 1 <= rowLen - 1) {
    steps.push({ row, col: high + 1 }); // East
    steps.push(...nonCoaxialSteps(row, high + 1, nRows)); // East diagonals
  }
  for (let i = low; i <= high; i += 1) {
    steps.push(...nonCoaxialSteps(row, i, nRows)); // North & South
  }
  return steps;
};

// part-1
let part1Solution = 0;
for (let rowIndex = 0; rowIndex < schema.length; rowIndex += 1) {
  const row = schema[rowIndex];
  for (const number of ints(row)) {
    const pivot = { row: rowIndex, col: number["index"] };
    const strNumber = number[0];
    part1Solution += adjacentSteps(pivot, strNumber, schema.length, row.length)
      .reduce((acc, step) => acc + schema[step.row][step.col], "")
      .match("[*#$@&%+/=-]")
      ? +strNumber
      : 0;
  }
}
console.log(part1Solution);

// part-2
const partNums = {};
for (let rowIndex = 0; rowIndex < schema.length; rowIndex += 1) {
  const row = schema[rowIndex];
  for (const number of ints(row)) {
    const pivot = { row: rowIndex, col: number["index"] };
    const strNumber = number[0];
    const steps = adjacentSteps(pivot, strNumber, schema.length, row.length);
    for (const step of steps) {
      if (schema[step.row][step.col].match("\\*")) {
        const partNumber = partNums[`${JSON.stringify(step)}`];
        partNums[`${JSON.stringify(step)}`] = partNumber
          ? [...partNumber, +strNumber]
          : [+strNumber];
      }
    }
  }
}
const part2Solution = Object.values(partNums).reduce((sum, partNumbers) => {
  return partNumbers[1] ? sum + partNumbers[0] * partNumbers[1] : sum;
}, 0);
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
