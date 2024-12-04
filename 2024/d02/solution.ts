import { readInput } from '../utils.js';

const start = performance.now();

function containsSafeReport(subLevels: ReadonlyArray<ReadonlyArray<number>>) {
  return subLevels.some((subLevel) => {
    let desc = 0;
    let asc = 0;
    for (let i = 1; i <= subLevel.length - 1; i += 1) {
      const prev = subLevel[i - 1];
      const curr = subLevel[i];
      if (prev > curr) desc += 1;
      else asc += 1;
      const delta = Math.abs(prev - curr);
      const isUnsafeReport = (desc > 0 && asc > 0) || delta < 1 || delta > 3;
      if (isUnsafeReport) return false;
    }
    return true;
  });
}

const unsafeReports = (reports: string[], allowTolerance: boolean) =>
  reports.reduce((unsafeReports, report) => {
    const levels = report.split(/\s/).map((n) => +n);
    const subLevels = [levels.toSpliced(0, 0)];
    if (allowTolerance)
      for (let i = 0; i <= levels.length - 1; i += 1)
        subLevels.push(levels.toSpliced(i, 1));
    if (!containsSafeReport(subLevels)) return unsafeReports + 1;
    return unsafeReports;
  }, 0);

const input = await readInput();
const reports = input[0].split(/\n/);
const totalReports = reports.length;

// part-1
const part1Solution = totalReports - unsafeReports(reports, false);
console.log(part1Solution);

// part-2
const part2Solution = totalReports - unsafeReports(reports, true);
console.log(part2Solution);

console.log('Elapsed:', performance.now() - start);
