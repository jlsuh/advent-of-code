import { readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const instr = input[0].split("");
const network = input
  .slice(2)
  .map((nodes) => [...nodes.matchAll("[A-Z]+")])
  .join(" ")
  .split(" ")
  .reduce((map, curr) => {
    const [src, lDest, rDest] = curr.split(",");
    map.set(src, [lDest, rDest]);
    return map;
  }, new Map());
let currNode = "AAA";
let instrIndex = 0;
let part1Solution = 0;
while (currNode != "ZZZ") {
  const currInstr = instr[instrIndex];
  const destNodes = network.get(currNode);
  currNode = currInstr === "L" ? destNodes[0] : destNodes[1];
  instrIndex += 1;
  if (instrIndex === instr.length) instrIndex = 0;
  part1Solution += 1;
}
console.log(part1Solution);

console.log("Elapsed:", performance.now() - start);
