import { readInput } from "../utils.js";

const start = performance.now();
const input = await readInput();
console.log(input);

let [startRow, startCol] = <[number, number]>(() => {
  for (let i = 0; i < input.length; i += 1) {
    const pipe = input[i];
    if (pipe.includes("S")) return [i, pipe.indexOf("S")];
  }
})();
console.log(startRow, startCol);

console.log("Elapsed:", performance.now() - start);
