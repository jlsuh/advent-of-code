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
    const chunks: Uint8Array[] = [];
    const readableStream = fs.createReadStream(FILE_PATH, UTF8);
    readableStream.on("data", (chunk) => {
      const encoder = new TextEncoder();
      const uintArrayValue = encoder.encode(chunk.toString());
      chunks.push(uintArrayValue);
    });
    readableStream.on("error", (error) => reject(error));
    readableStream.on("end", () =>
      resolve(Buffer.concat(chunks).toString(UTF8).split(/\r\n/)),
    );
  });

export { readInput };
