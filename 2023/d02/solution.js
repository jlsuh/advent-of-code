import { readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const NUMBER = "\\d+";

const games = input
  .split("\n")
  .map((game) => game.match(`${NUMBER}\\s(.*)`)[0])
  .map((draws) => draws.split("; "))
  .map((draw) => draw.map((cubes) => cubes.split(", ")));

const getCubeInfo = (cube, pattern) => {
  return {
    color: cube.match(pattern)[0],
    quantity: cube.match(`${NUMBER}`)[0],
  };
};

// part-1
const max = {
  red: 12,
  green: 13,
  blue: 14,
};
const part1Solution = games.reduce((sum, game, index) => {
  for (const draws of game) {
    for (const cube of draws) {
      const { color, quantity } = getCubeInfo(
        cube,
        `${Object.keys(max).join("|")}`,
      );
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
    for (const cube of draws) {
      const { color, quantity } = getCubeInfo(
        cube,
        `${Object.keys(fewestCubes).join("|")}`,
      );
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
