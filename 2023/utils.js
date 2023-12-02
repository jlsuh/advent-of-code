import fs from "fs";
import { dirname, join, sep } from "path";
import { fileURLToPath } from "url";

const ints = (string) => {
  return string.match(/\d+/g);
};

const alts = (...objects) =>
  objects.reduce(
    (pattern, object) => pattern + Object.keys(object).join("|"),
    "",
  );

const __dirname = dirname(fileURLToPath(import.meta.url));
const SUBDIRS = process.argv[1].split(sep);
const DAY_SUBDIR = SUBDIRS[SUBDIRS.length - 2];
const INPUT_FILE = "input.txt";
const FILE_PATH = join(__dirname, DAY_SUBDIR, INPUT_FILE);
const UTF8 = "utf8";

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

export { alts, ints, readInput };
