import { nums, readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const histories = input
  .map((history) => nums(history).join(" ").split(" "))
  .map((history) => history.map((num) => +num));
const extrapolations: {
  (backwards: boolean): ReadonlyArray<ReadonlyArray<ReadonlyArray<number>>>;
} = (backwards: boolean) =>
  histories.reduce<ReadonlyArray<ReadonlyArray<ReadonlyArray<number>>>>(
    (extrapolations, history) => {
      let seqs: ReadonlyArray<Array<number>> = [history];
      let lastSeq = seqs[seqs.length - 1];
      for (; lastSeq.some((n) => n !== 0); lastSeq = seqs[seqs.length - 1]) {
        let newSeq: Array<number> = [];
        for (let i = 1; i < lastSeq.length; i += 1) {
          newSeq = [...newSeq, lastSeq[i] - lastSeq[i - 1]];
        }
        seqs = [...seqs, newSeq];
      }
      lastSeq.push(0);
      for (let i = seqs.length - 1; i > 0; i -= 1) {
        const [curr, prev] = [seqs[i], seqs[i - 1]];
        if (backwards) prev.unshift(prev[0] - curr[0]);
        else prev.push(prev[prev.length - 1] + curr[curr.length - 1]);
      }
      return [...extrapolations, seqs];
    },
    [],
  );

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
