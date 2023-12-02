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

export { alts, ints };
