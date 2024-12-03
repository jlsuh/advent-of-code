import { readInput } from "../utils.js";

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

const input = await readInput();
const reports: ReadonlyArray<string> = input[0].split(/\n/);
const totalReports = reports.length;

// part-1
console.log(
  totalReports -
    reports.reduce((unsafeReports, report) => {
      const levels = report.split(/\s/).map((n) => +n);
      const subLevels = [levels.toSpliced(0, 0)];
      if (!containsSafeReport(subLevels)) return unsafeReports + 1;
      return unsafeReports;
    }, 0),
);

// part-2
console.log(
  totalReports -
    reports.reduce((unsafeReports, report) => {
      const levels = report.split(/\s/).map((n) => +n);
      const subLevels = [levels.toSpliced(0, 0)];
      for (let i = 0; i <= levels.length - 1; i += 1)
        subLevels.push(levels.toSpliced(i, 1));
      if (!containsSafeReport(subLevels)) return unsafeReports + 1;
      return unsafeReports;
    }, 0),
);

console.log("Elapsed:", performance.now() - start);
