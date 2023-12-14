import { nums, readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const histories = input
  .map((history) => nums(history).join(" ").split(" "))
  .map((history) => history.map((num) => +num));
const extrapolations = (backwards) => {
  let extrapolations = [];
  for (const history of histories) {
    let sequences = [history];
    while (sequences[sequences.length - 1].some((n) => n !== 0)) {
      const lastSequence = sequences[sequences.length - 1];
      let newSequence = [];
      for (let i = 1; i < lastSequence.length; i += 1) {
        newSequence = [...newSequence, lastSequence[i] - lastSequence[i - 1]];
      }
      sequences = [...sequences, newSequence];
    }
    sequences[sequences.length - 1].push(0);
    for (let i = sequences.length - 1; i > 0; i -= 1) {
      if (backwards) {
        sequences[i - 1].unshift(sequences[i - 1][0] - sequences[i][0]);
      } else {
        sequences[i - 1].push(
          sequences[i][sequences[i].length - 1] +
            sequences[i - 1][sequences[i - 1].length - 1],
        );
      }
    }
    extrapolations = [...extrapolations, sequences];
  }
  return extrapolations;
};

// part-1
const part1Solution = extrapolations(false)
  .map((extrapolation) => extrapolation[0][extrapolation[0].length - 1])
  .reduce((sum, curr) => sum + curr, 0);
console.log(part1Solution);

// part-2
const part2Solution = extrapolations(true)
  .map((extrapolation) => extrapolation[0][0])
  .reduce((sum, curr) => sum + curr, 0);
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
