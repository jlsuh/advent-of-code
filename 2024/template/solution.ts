import { readInput } from '../utils.ts';

const start = performance.now();
const input = await readInput();
console.log('Elapsed:', performance.now() - start);
