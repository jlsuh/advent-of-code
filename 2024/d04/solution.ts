import { readInput } from '../utils.ts';

const start = performance.now();
const input = await readInput();
const lines = input[0].split('\n').map((line) => line.split(''));

const maxRow = lines.length - 1;
const maxCol = lines[0].length - 1;

// part-1
let part1Solution = 0;
for (let row = 0; row < lines.length; row += 1)
  for (let col = 0; col < lines[row].length; col += 1)
    if (lines[row][col] === 'X')
      for (const [M, A, S] of ((row, col, maxRow, maxCol, r = 3) =>
        [
          row - r >= 0 && col - r >= 0
            ? [
                [
                  [-1, -1],
                  [-2, -2],
                  [-3, -3],
                ],
              ]
            : [],
          row - r >= 0
            ? [
                [
                  [-1, 0],
                  [-2, 0],
                  [-3, 0],
                ],
              ]
            : [],
          row - r >= 0 && col + r <= maxCol
            ? [
                [
                  [-1, 1],
                  [-2, 2],
                  [-3, 3],
                ],
              ]
            : [],
          col + r <= maxCol
            ? [
                [
                  [0, 1],
                  [0, 2],
                  [0, 3],
                ],
              ]
            : [],
          row + r <= maxRow && col + r <= maxCol
            ? [
                [
                  [1, 1],
                  [2, 2],
                  [3, 3],
                ],
              ]
            : [],
          row + r <= maxRow
            ? [
                [
                  [1, 0],
                  [2, 0],
                  [3, 0],
                ],
              ]
            : [],
          row + r <= maxRow && col - r >= 0
            ? [
                [
                  [1, -1],
                  [2, -2],
                  [3, -3],
                ],
              ]
            : [],
          col - r >= 0
            ? [
                [
                  [0, -1],
                  [0, -2],
                  [0, -3],
                ],
              ]
            : [],
        ].flat())(row, col, maxRow, maxCol))
        if (
          lines[row + M[0]][col + M[1]] === 'M' &&
          lines[row + A[0]][col + A[1]] === 'A' &&
          lines[row + S[0]][col + S[1]] === 'S'
        )
          part1Solution += 1;
console.log(part1Solution);

// part-2
let part2Solution = 0;
for (let row = 0; row < lines.length; row += 1)
  for (let col = 0; col < lines[row].length; col += 1)
    if (lines[row][col] === 'A')
      for (const [NW, NE, SE, SW] of ((row, col, maxRow, maxCol, r = 1) =>
        row - r >= 0 && col - r >= 0 && row + r <= maxRow && col + r <= maxCol
          ? [
              [
                [-1, -1],
                [-1, 1],
                [1, 1],
                [1, -1],
              ],
            ]
          : [])(row, col, maxRow, maxCol)) {
        const NWChar = lines[row + NW[0]][col + NW[1]];
        const NEChar = lines[row + NE[0]][col + NE[1]];
        const SWChar = lines[row + SW[0]][col + SW[1]];
        const SEChar = lines[row + SE[0]][col + SE[1]];
        if (
          (NWChar === 'M' &&
            SEChar === 'S' &&
            ((SWChar === 'M' && NEChar === 'S') ||
              (SWChar === 'S' && NEChar === 'M'))) ||
          (NWChar === 'S' &&
            SEChar === 'M' &&
            ((SWChar === 'M' && NEChar === 'S') ||
              (SWChar === 'S' && NEChar === 'M')))
        )
          part2Solution += 1;
      }
console.log(part2Solution);

console.log('Elapsed:', performance.now() - start);
