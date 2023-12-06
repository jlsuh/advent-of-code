import { ints, readInput } from "../utils.js";

const start = performance.now();
const input = (await readInput()).filter((line) => line);

// part-1
const seeds = ints(input[0])
  .join(" ")
  .split(" ")
  .map((seed) => +seed);
const maps = input
  .toSpliced(0, 2)
  .map((input) => ints(input).join(" ").split(" "))
  .reduce(
    (maps, curr) => {
      if (curr[0]) maps[maps.length - 1].push(curr);
      else maps.push([]);
      return maps;
    },
    [[]],
  );
let part1Solution = Infinity;
for (let seed of seeds) {
  for (const map of maps) {
    for (const transform of map) {
      const [destStart, srcStart, rangeLen] = transform.map((num) => +num);
      const low = srcStart;
      const high = srcStart + rangeLen - 1;
      if (seed >= low && seed <= high) {
        const destOffset = seed - srcStart;
        const transformed = destStart + destOffset;
        seed = transformed;
        break;
      }
    }
  }
  part1Solution = Math.min(part1Solution, seed);
}
console.log(part1Solution);

console.log("Elapsed:", performance.now() - start);
