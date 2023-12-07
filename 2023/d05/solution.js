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

// part-2
function intersect(arr1, arr2) {
  const [low1, high1] = arr1;
  const [low2, high2] = arr2;
  const low = Math.max(low1, low2);
  const high = Math.min(high1, high2);
  if (low > high) return null;
  return [low, high];
}

function nonIntersectingIntervals(seedInterval, intersectingIntervals) {
  const nonIntersectingIntervals = [];
  const [seedLow, seedHigh] = seedInterval;
  const [firstLow] = intersectingIntervals[0];
  if (firstLow - seedLow > 0) {
    nonIntersectingIntervals.push([seedLow, firstLow - 1]);
  }
  const [, lastHigh] = intersectingIntervals[intersectingIntervals.length - 1];
  if (seedHigh - lastHigh > 0) {
    nonIntersectingIntervals.push([lastHigh + 1, seedHigh]);
  }
  for (let i = 0; i < intersectingIntervals.length - 1; i += 1) {
    const [, currHigh] = intersectingIntervals[i];
    const [nextLow] = intersectingIntervals[i + 1];
    if (nextLow - currHigh - 1 > 0) {
      nonIntersectingIntervals.push([currHigh + 1, nextLow - 1]);
    }
  }
  return nonIntersectingIntervals.toSorted((int1, int2) => int1[0] - int2[0]);
}

let newSeeds = JSON.parse(JSON.stringify(seeds));
for (const map of maps) {
  let currMapSeeds = [];
  for (let i = 0; i < newSeeds.length; i += 2) {
    const intersectionIntervals = [];
    const transformedIntersectionIntervals = [];
    const nonIntersectionIntervals = [];
    let seedInterval = [newSeeds[i], newSeeds[i] + newSeeds[i + 1] - 1];
    for (const transform of map.toSorted((t1, t2) => t1[1] - t2[1])) {
      const [destStart, intervalLow, rangeLen] = transform.map((num) => +num);
      const intervalHigh = intervalLow + rangeLen - 1;
      const interval = [intervalLow, intervalHigh];
      const intersectionInterval = intersect(seedInterval, interval);
      if (intersectionInterval) {
        intersectionIntervals.push(intersectionInterval);
        const transformed = [
          intersectionInterval[0] + destStart - intervalLow,
          intersectionInterval[1] + destStart - intervalLow,
        ];
        transformedIntersectionIntervals.push(transformed);
      }
    }
    if (intersectionIntervals.length) {
      nonIntersectionIntervals.push(
        ...nonIntersectingIntervals(seedInterval, intersectionIntervals),
      );
    } else {
      nonIntersectionIntervals.push(seedInterval);
    }
    const resultingMapIntervals = [
      ...nonIntersectionIntervals,
      ...transformedIntersectionIntervals,
    ];
    for (const interval of resultingMapIntervals) {
      currMapSeeds.push(interval[0]);
      currMapSeeds.push(interval[1] - interval[0] + 1);
    }
  }
  newSeeds = currMapSeeds;
}
const part2Solution = newSeeds.reduce((acc, curr, i) => {
  if (i % 2 === 0) return Math.min(acc, curr);
  return acc;
}, Infinity);
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
