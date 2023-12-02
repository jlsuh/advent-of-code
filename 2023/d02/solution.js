import { readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const NUMBER = "\\d+";

const games = input
  .split("\n")
  .map((game) => game.match(`${NUMBER}\\s(.*)`)[0])
  .map((gameInfo) => gameInfo.split("; "))
  .map((draw) => draw.map((cubes) => cubes.split(", ")));

// part-1
const max = {
  red: 12,
  green: 13,
  blue: 14,
};
const part1Solution = games.reduce((sum, game, index) => {
  for (const draws of game) {
    for (const cubes of draws) {
      const color = cubes.match(`${Object.keys(max).join("|")}`)[0];
      const quantity = cubes.match(`${NUMBER}`)[0];
      if (quantity > max[color]) return sum;
    }
  }
  return sum + index + 1;
}, 0);
console.log(part1Solution);

// part-2
const part2Solution = games.reduce((sum, game) => {
  const fewestCubes = {
    red: 0,
    green: 0,
    blue: 0,
  };
  for (const draws of game) {
    for (const cubes of draws) {
      const color = cubes.match(`${Object.keys(fewestCubes).join("|")}`)[0];
      const quantity = cubes.match(`${NUMBER}`)[0];
      fewestCubes[color] = Math.max(fewestCubes[color], quantity);
    }
  }
  const power = Object.values(fewestCubes).reduce(
    (prod, curr) => prod * curr,
    1,
  );
  return sum + power;
}, 0);
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
