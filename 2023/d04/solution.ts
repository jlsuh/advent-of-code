import { range, readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const cardParts = input.map((cardInfo) => cardInfo.split(/\s\|\s/));
const cardNums = cardParts
  .map((parts) =>
    parts.map((part) =>
      ((match) => {
        if (match === null) throw new Error(":)");
        return match[0];
      })(part.match(/\d+\s.*/)),
    ),
  )
  .map((numList) => numList.map((nums) => nums.split(/\s+/)));

// part-1
const part1Solution = cardNums
  .map((cardNums) => cardNums[1].filter((num) => cardNums[0].includes(num)))
  .map((wNums) => (wNums.length ? Math.pow(2, wNums.length - 1) : 0))
  .reduce((sum, curr) => sum + curr, 0);
console.log(part1Solution);

// part-2
const instances: {
  [key: string]: number;
} = range(1, cardParts.length, 1).reduce(
  (instances, id) => ({
    ...instances,
    [`${id}`]: 1,
  }),
  {},
);
let part2Solution = 0;
for (let i = 0; i < cardParts.length; i += 1) {
  const wNumsLen = cardNums[i][1].filter((num) =>
    cardNums[i][0].includes(num),
  ).length;
  const cardId = i + 1;
  const cardInstances = instances[`${cardId}`];
  for (const aboveId of range(cardId + 1, cardId + wNumsLen, 1)) {
    instances[`${aboveId}`] += cardInstances;
  }
  part2Solution += cardInstances;
}
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
