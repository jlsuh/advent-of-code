import { readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();

const instr = input[0].split("");
const network = input
  .slice(2)
  .map((nodes) => [...nodes.matchAll("[A-Z1-9]+")])
  .join(" ")
  .split(" ")
  .reduce((map, curr) => {
    const [src, lDest, rDest] = curr.split(",");
    map.set(src, [lDest, rDest]);
    return map;
  }, new Map());

const stepsTaken = (initialNodes) => {
  const stepsTaken = [];
  for (let currNode of initialNodes) {
    let instrIndex = 0;
    let steps = 0;
    while (currNode[2] !== "Z") {
      const currInstr = instr[instrIndex];
      const destNodes = network.get(currNode);
      currNode = currInstr === "L" ? destNodes[0] : destNodes[1];
      instrIndex += 1;
      if (instrIndex === instr.length) instrIndex = 0;
      steps += 1;
    }
    stepsTaken.push(steps);
  }
  return stepsTaken;
};

// part-1
const part1Solution = stepsTaken(["AAA"])[0];
console.log(part1Solution);

// part-2
const limitNodes = [...network.keys()]
  .flatMap((key) => [...key.matchAll(/[A-Z1-9]+[AZ]/g)])
  .join(" ")
  .split(" ")
  .toSorted((a, b) => a[2].localeCompare(b[2]));
const part2Solution = stepsTaken(limitNodes.slice(0, limitNodes.length / 2));
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
