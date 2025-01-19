import { readInput } from '../utils.ts';

const start = performance.now();
const input = await readInput();
const [rules, updates] = input[0].split(/\n\n/);
const precedenceMap: ReadonlyMap<string, ReadonlyArray<string>> = rules
  .split(/\n/)
  .map((rule) => rule.split(/\|/))
  .reduce((precedenceMap, [predecessor, successor]) => {
    const successors = precedenceMap.get(predecessor);
    if (successors) precedenceMap.set(predecessor, [...successors, successor]);
    else precedenceMap.set(predecessor, [successor]);
    return precedenceMap;
  }, new Map<string, string[]>());

const sumMiddlePages = (pages: string[]) =>
  pages
    .map((update) => {
      const pages = update.split(/,/);
      return pages[Math.floor(pages.length / 2)];
    })
    .reduce((sum, curr) => sum + +curr, 0);

// part-1 & part-2
const correctlyOrderedUpdates: string[] = [];
const incorrectlyOrderedUpdates: string[] = [];
for (const update of updates.split(/\n/)) {
  let isIncorrectlyOrderedUpdate = false;
  const pages = update.split(/,/);
  for (let i = 0; i < pages.length; i += 1) {
    const head = pages[i];
    const tail = pages.slice(i + 1);
    const successors = precedenceMap.get(head) ?? [];
    if (
      !tail.every((page) => successors.includes(page)) ||
      (successors.length === 0 && tail.length > 0)
    ) {
      isIncorrectlyOrderedUpdate = true;
      break;
    }
  }
  if (isIncorrectlyOrderedUpdate) {
    incorrectlyOrderedUpdates.push(update);
    continue;
  }
  correctlyOrderedUpdates.push(update);
}
const part1Solution = sumMiddlePages(correctlyOrderedUpdates);
console.log(part1Solution);

// part-2
for (let i = 0; i < incorrectlyOrderedUpdates.length; i += 1) {
  let pages = incorrectlyOrderedUpdates[i].split(/,/);
  for (let j = 0; j < pages.length; j += 1) {
    const head = pages[j];
    const tail = pages.slice(j + 1);
    const successors = precedenceMap.get(head) ?? [];
    if (successors.length === 0 && j < pages.length - 1) {
      const lastElem = pages.splice(j, 1)[0];
      pages.push(lastElem);
      j -= 1;
      continue;
    }
    const incorrectlyOrderedPages = tail.reduce<string[]>(
      (pages, page) => (successors.includes(page) ? pages : [...pages, page]),
      [],
    );
    if (incorrectlyOrderedPages.length > 0) {
      for (const incorrectlyOrderedPage of incorrectlyOrderedPages)
        pages = pages.toSpliced(pages.indexOf(incorrectlyOrderedPage), 1);
      pages = [
        ...pages.slice(0, j),
        ...incorrectlyOrderedPages,
        ...pages.slice(j),
      ];
      j -= 1;
    }
  }
  incorrectlyOrderedUpdates[i] = pages.join();
}
const part2Solution = sumMiddlePages(incorrectlyOrderedUpdates);
console.log(part2Solution);

console.log('Elapsed:', performance.now() - start);
