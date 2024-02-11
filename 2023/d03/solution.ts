import { ints, readInput } from "../utils.js";

type Pivot = {
  row: number;
  col: number;
};

const start = performance.now();
const input = await readInput();

const nonCoaxialSteps = (row: number, col: number, nRows: number) => {
  const steps = [];
  if (row - 1 >= 0) {
    steps.push({ row: row - 1, col });
  }
  if (row + 1 <= nRows - 1) {
    steps.push({ row: row + 1, col });
  }
  return steps;
};
const adjacentSteps = (
  { row, col }: Pivot,
  content: string,
  nRows: number,
  rowLen: number,
) => {
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
for (let rowIndex = 0; rowIndex < input.length; rowIndex += 1) {
  const row = input[rowIndex];
  for (const num of ints(row)) {
    const pivot: Pivot = {
      row: rowIndex,
      col:
        num["index"] ??
        (() => {
          throw new Error(":)");
        })(),
    };
    const strNum = num[0];
    part1Solution += adjacentSteps(pivot, strNum, input.length, row.length)
      .reduce((sum, step) => sum + input[step.row][step.col], "")
      .match(/[*#$@&%+/=-]/)
      ? +strNum
      : 0;
  }
}
console.log(part1Solution);

// part-2
const partNums: {
  [key: string]: ReadonlyArray<number>;
} = {};
for (let rowIndex = 0; rowIndex < input.length; rowIndex += 1) {
  const row = input[rowIndex];
  for (const num of ints(row)) {
    const pivot: Pivot = {
      row: rowIndex,
      col:
        num["index"] ??
        (() => {
          throw new Error(":)");
        })(),
    };
    const strNum = num[0];
    const steps = adjacentSteps(pivot, strNum, input.length, row.length);
    for (const step of steps) {
      if (input[step.row][step.col].match(/\*/)) {
        const partNum = partNums[`${JSON.stringify(step)}`];
        partNums[`${JSON.stringify(step)}`] = partNum
          ? [...partNum, +strNum]
          : [+strNum];
      }
    }
  }
}
const part2Solution = Object.values(partNums).reduce(
  (sum, partNums) => (partNums[1] ? sum + partNums[0] * partNums[1] : sum),
  0,
);
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
