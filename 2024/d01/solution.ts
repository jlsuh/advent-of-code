import { readInput } from '../utils.ts';

const start = performance.now();
const input = await readInput();

const [fsts, snds] = input[0]
  .split(/\n/)
  .reduce<Array<Array<number>>>(
    (lines, line) => {
      const [fst, snd] = line.split(/\s+/).map((n) => +n);
      return [
        [...lines[0], fst],
        [...lines[1], snd],
      ];
    },
    [[], []],
  )
  .map((lines) => lines.toSorted());

// part-1
const part1Solution = fsts.reduce(
  (sum, curr, i) => sum + Math.abs(curr - snds[i]),
  0,
);
console.log(part1Solution);

// part-2
const sndsCount = snds.reduce((map, curr) => {
  const key = '' + curr;
  if (map.has(key)) map.set(key, (map.get(key) ?? 0) + 1);
  else map.set(key, 1);
  return map;
}, new Map<string, number>());
const part2Solution = fsts.reduce(
  (sum, curr) => sum + curr * (sndsCount.get('' + curr) ?? 0),
  0,
);
console.log(part2Solution);

console.log('Elapsed:', performance.now() - start);
