import fs from "fs";
import { dirname, join, sep } from "path";
import { fileURLToPath } from "url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const SUBDIRS = process.argv[1].split(sep);
const DAY_SUBDIR = SUBDIRS[SUBDIRS.length - 2];
const INPUT_FILE = "input.txt";
const FILE_PATH = join(__dirname, DAY_SUBDIR, INPUT_FILE);
const UTF8 = "utf8";

const readInput = () =>
  new Promise<string[]>((resolve, reject) => {
    const chunks: Buffer[] = [];
    const readableStream = fs.createReadStream(FILE_PATH, UTF8);
    readableStream.on("data", (chunk) => chunks.push(Buffer.from(chunk)));
    readableStream.on("error", (error) => reject(error));
    readableStream.on("end", () =>
      resolve(Buffer.concat(chunks).toString(UTF8).split(/\r\n/)),
    );
  });

const alts = (...objects: object[]) =>
  objects.reduce(
    (pattern, object) => pattern + Object.keys(object).join("|"),
    "",
  );

const ints = (string: string) =>
  [...string.matchAll(/\d+/g)].map((match) => match);

const nums = (string) => [...string.matchAll(/-?\d*\.?\d+/g)];

const range = (start: number, stop: number, step: number) =>
  Array.from({ length: (stop - start) / step + 1 }, (_, i) => start + i * step);

const charCounts = (str) => {
  const counts = new Map();
  for (const c of str) counts.set(c, (counts.get(c) ?? 0) + 1);
  return counts;
};

const gcd = (a, b) => {
  while (b !== 0) [a, b] = [b, a % b];
  return a;
};

const lcm = (a, b) => Math.abs(a * b) / gcd(a, b);

export { alts, charCounts, ints, lcm, nums, range, readInput };
