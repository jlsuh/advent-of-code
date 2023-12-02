import { alts, ints, readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const games = input
  .split("\n")
  .map((game) => game.match(`\\d+\\s(.*)`)[0])
  .map((draws) => draws.split("; "))
  .map((draw) => draw.map((cubes) => cubes.split(", ")));

const getCubeInfo = (cube, colorsPattern) => ({
  color: cube.match(colorsPattern)[0],
  quantity: ints(cube)[0],
});

// part-1
const max = {
  red: 12,
  green: 13,
  blue: 14,
};
const part1Solution = games.reduce((sum, game, index) => {
  for (const draws of game) {
    for (const cube of draws) {
      const { color, quantity } = getCubeInfo(cube, alts(max));
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
      const { color, quantity } = getCubeInfo(cube, alts(fewestCubes));
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
