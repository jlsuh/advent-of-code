import { charCounts, readInput } from "../utils.js";

const start = performance.now();
const input = (await readInput()).map((input) => input.split(" "));

// part-1
const cardsRank = {
  5: 7,
  41: 6,
  32: 5,
  311: 4,
  221: 3,
  2111: 2,
  11111: 1,
};
const cardStrengh = {
  A: 13,
  K: 12,
  Q: 11,
  J: 10,
  T: 9,
  9: 8,
  8: 7,
  7: 6,
  6: 5,
  5: 4,
  4: 3,
  3: 2,
  2: 1,
};
const sortedCardsRank = (instances) => {
  return [...instances.values()].toSorted((i1, i2) => i2 - i1).join("");
};
const sortedHands = input.toSorted((hand1, hand2) => {
  const [cards1, cards2] = [hand1[0], hand2[0]];
  const [counts1, counts2] = [charCounts(cards1), charCounts(cards2)];
  const [rank1, rank2] = [
    cardsRank[sortedCardsRank(counts1)],
    cardsRank[sortedCardsRank(counts2)],
  ];
  if (rank1 < rank2) {
    return 1;
  } else if (rank1 > rank2) {
    return -1;
  }
  for (let i = 0; i < cards1.length; i += 1) {
    const [strength1, strength2] = [
      cardStrengh[cards1[i]],
      cardStrengh[cards2[i]],
    ];
    if (strength1 < strength2) {
      return 1;
    } else if (strength1 > strength2) {
      return -1;
    }
  }
  return 0;
});
const part1Solution = sortedHands.reduce(
  (sum, curr, i, arr) => sum + curr[1] * (arr.length - i),
  0,
);
console.log(part1Solution);

console.log("Elapsed:", performance.now() - start);
