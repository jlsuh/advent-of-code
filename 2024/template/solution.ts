import { readInput } from '../utils.js';

const start = performance.now();
const input = await readInput();
console.log('Elapsed:', performance.now() - start);
