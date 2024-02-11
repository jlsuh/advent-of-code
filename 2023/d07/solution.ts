import { charCounts, readInput } from "../utils.js";

const start = performance.now();
const input = (await readInput()).map((input) => input.split(" "));

const cardsRank = ["11111", "2111", "221", "311", "32", "41", "5"];
const sortedCardsRank = (cards: string, withJokers: boolean) => {
  const initialInstances = charCounts(cards);
  const instancesWithoutJokers = new Map(initialInstances);
  let instances = initialInstances;
  if (withJokers && cards !== "JJJJJ") {
    if (instancesWithoutJokers.delete("J")) {
      const instancesEntries = [...instancesWithoutJokers.entries()];
      let [maxLabel] =
        instancesEntries.shift() ??
        (() => {
          throw new Error("No cards in hand");
        })();
      for (const entry of instancesEntries) {
        maxLabel =
          initialInstances.get(maxLabel) >= initialInstances.get(entry[0])
            ? maxLabel
            : entry[0];
      }
      instancesWithoutJokers.set(
        maxLabel,
        instancesWithoutJokers.get(maxLabel) + initialInstances.get("J"),
      );
      instances = instancesWithoutJokers;
    }
  }
  return [...instances.values()].toSorted((i1, i2) => i2 - i1).join("");
};
const sortHands = (withJokers: boolean, cardStrength: string) =>
  input.toSorted((hand1, hand2) => {
    const [cards1, cards2] = [hand1[0], hand2[0]];
    const [rank1, rank2] = [
      cardsRank.indexOf(sortedCardsRank(cards1, withJokers)),
      cardsRank.indexOf(sortedCardsRank(cards2, withJokers)),
    ];
    if (rank1 > rank2) return 1;
    else if (rank1 < rank2) return -1;
    for (let i = 0; i < cards1.length; i += 1) {
      const [strength1, strength2] = [
        cardStrength.indexOf(cards1[i]),
        cardStrength.indexOf(cards2[i]),
      ];
      if (strength1 > strength2) return 1;
      else if (strength1 < strength2) return -1;
    }
    throw new Error(":)");
  });
const totalWinnings = (sortedHands: ReadonlyArray<ReadonlyArray<string>>) =>
  sortedHands.reduce((sum, curr, i) => sum + +curr[1] * (i + 1), 0);

// part-1
const part1Solution = totalWinnings(sortHands(false, "23456789TJQKA"));
console.log(part1Solution);

// part-2
const part2Solution = totalWinnings(sortHands(true, "J23456789TQKA"));
console.log(part2Solution);

console.log("Elapsed:", performance.now() - start);
