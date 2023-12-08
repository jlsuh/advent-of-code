import { ints, readInput } from "../utils.js";

const start = performance.now();
const input = (await readInput()).map((info) =>
  ints(info).join(" ").split(" "),
);

// part-1
let beatableWays = [...Array(input[0].length).fill(0)];
for (let race = 0; race < input[0].length; race += 1) {
  const [times, distances] = input;
  for (let hold = 0; hold <= times[race]; hold += 1) {
    const travelDist = hold * (times[race] - hold);
    if (travelDist > distances[race]) beatableWays[race] += 1;
  }
}
const part1Solution = beatableWays.reduce((prod, curr) => prod * curr, 1);
console.log(part1Solution);

// part-2
const [time, dist] = input.map((info) => info.join("")).map((num) => +num);
const quadratic = (a, b, c) => {
  const discriminant = Math.pow(b, 2) - 4 * a * c;
  return [
    (-b - Math.sqrt(discriminant)) / (2 * a),
    (-b + Math.sqrt(discriminant)) / (2 * a),
  ];
};
const roots = quadratic(1, -time, dist);
const f = (x) => Math.pow(x, 2) - time * x + dist;
const intRoots = roots
  .flatMap((root) => [Math.floor(root), Math.ceil(root)])
  .filter((root) => f(root) < 0);
const part2Solution = Math.abs(intRoots[0] - intRoots[1]) + 1;
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
