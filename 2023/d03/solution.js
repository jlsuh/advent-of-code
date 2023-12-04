import { ints, readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const schema = input.split("\r\n");

const nonCoaxialSteps = (row, col, schemaLines) => {
  const steps = [];
  if (row - 1 >= 0) {
    steps.push({ row: row - 1, col });
  }
  if (row + 1 <= schemaLines - 1) {
    steps.push({ row: row + 1, col });
  }
  return steps;
};

const adjacentSteps = ({ row, col }, symbol, schemaLines, lineLength) => {
  const low = col;
  const high = col + symbol.length - 1;
  const steps = [];
  if (low - 1 >= 0) {
    steps.push({ row, col: low - 1 }); // West
    steps.push(...nonCoaxialSteps(row, low - 1, schemaLines)); // West diagonals
  }
  if (high + 1 <= lineLength - 1) {
    steps.push({ row, col: high + 1 }); // East
    steps.push(...nonCoaxialSteps(row, high + 1, schemaLines)); // East diagonals
  }
  for (let i = low; i <= high; i += 1) {
    steps.push(...nonCoaxialSteps(row, i, schemaLines)); // North & South
  }
  return steps;
};

// part-1
let part1Solution = 0;

for (let row = 0; row < schema.length; row += 1) {
  const line = schema[row];
  for (const numbers of ints(line)) {
    const pivot = { row, col: numbers["index"] };
    const steps = adjacentSteps(pivot, numbers[0], schema.length, line.length);
    const symbols = steps.reduce(
      (acc, step) => acc + schema[step.row][step.col],
      "",
    );
    part1Solution += symbols.match("[*#$@&%+/=-]") ? +numbers[0] : 0;
  }
}

console.log(part1Solution);

// part-2
const partNumbers = {};
for (let row = 0; row < schema.length; row += 1) {
  const line = schema[row];
  for (const number of ints(line)) {
    const pivot = { row, col: number["index"] };
    const steps = adjacentSteps(pivot, number[0], schema.length, line.length);
    for (const step of steps) {
      if (schema[step.row][step.col].match("\\*")) {
        const partNumber = partNumbers[`${JSON.stringify(step)}`];
        partNumbers[`${JSON.stringify(step)}`] = partNumber
          ? [...partNumber, +number[0]]
          : [+number[0]];
      }
    }
  }
}

const part2Solution = Object.values(partNumbers).reduce((sum, partNumbers) => {
  return partNumbers[1] ? sum + partNumbers[0] * partNumbers[1] : sum;
}, 0);

console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
