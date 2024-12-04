import { readInput } from '../utils.js';

const start = performance.now();
const input = await readInput();

const multiplyPairs = (instr: string) =>
  (([fst, snd]) => +fst[0] * +snd[0])([...instr.matchAll(/\d+/g)]);

// part-1
const part1Solution = input[0]
  .matchAll(/mul\(\d+,\d+\)/g)
  .reduce((sum, [instr]) => sum + multiplyPairs(instr), 0);
console.log(part1Solution);

// part-2
let isEnabled = true;
const part2Solution = input[0]
  .matchAll(/mul\(\d+,\d+\)|do\(\)|don't\(\)/g)
  .reduce((sum, [instr]) => {
    if (instr.match(/do\(\)/)) isEnabled = true;
    else if (instr.match(/don't\(\)/)) isEnabled = false;
    else if (isEnabled) return sum + multiplyPairs(instr);
    return sum;
  }, 0);
console.log(part2Solution);

console.log('Elapsed:', performance.now() - start);
