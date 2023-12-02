import fs from "fs";
import { dirname, join, sep } from "path";
import { fileURLToPath } from "url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const SUBDIRS = process.argv[1].split(sep);
const DAY_SUBDIR = SUBDIRS[SUBDIRS.length - 2];
const INPUT_FILE = "input.txt";
const FILE_PATH = join(__dirname, DAY_SUBDIR, INPUT_FILE);
const UTF8 = "utf8";

/**
 * Reads the input file in the same directory as the solution file
 * and returns a promise of the file's contents as a string
 * @returns {Promise<string>}
 */
const readInput = () => {
  return new Promise((resolve, reject) => {
    const chunks = [];
    const readableStream = fs.createReadStream(FILE_PATH, UTF8);
    readableStream.on("data", (chunk) => chunks.push(Buffer.from(chunk)));
    readableStream.on("error", (error) => reject(error));
    readableStream.on("end", () =>
      resolve(Buffer.concat(chunks).toString(UTF8)),
    );
  });
};

/**
 * Returns a string of all keys in the objects joined by "|" (alternation)
 * @param  {...object} objects
 * @returns {string}
 */
const alts = (...objects) =>
  objects.reduce(
    (pattern, object) => pattern + Object.keys(object).join("|"),
    "",
  );

/**
 * Returns an array of all integers in the string
 * @param {string} string
 * @returns {string[]}
 */
const ints = (string) => {
  return string.match(/\d+/g);
};

export { alts, ints, readInput };
