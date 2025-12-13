const std = @import("std");

var it = std.mem.splitAny(u8, @embedFile("input.txt"), "\n");
const max_dial_size = 100;
const initial_dial = 50;

pub fn fstPart() !u16 {
    var dial: i32 = initial_dial;
    var times_pointing_at_zero: u16 = 0;
    while (it.next()) |rotation| {
        const distance: u16 = try std.fmt.parseUnsigned(u16, rotation[1..], 10);
        dial = @mod(if (rotation[0] == 'R') dial + distance else dial - distance, max_dial_size);
        if (dial == 0) times_pointing_at_zero += 1;
    }
    return times_pointing_at_zero;
}

pub fn sndPart() !u16 {
    var dial: u16 = initial_dial;
    var times_pointing_at_zero: u16 = 0;
    while (it.next()) |rotation| {
        var distance: u16 = try std.fmt.parseUnsigned(u16, rotation[1..], 10);
        times_pointing_at_zero += distance / max_dial_size;
        distance = @mod(distance, max_dial_size);
        if (distance == 0) continue;
        if (rotation[0] == 'R') {
            dial += distance;
            if (dial >= max_dial_size) {
                dial = @mod(dial, max_dial_size);
                times_pointing_at_zero += 1;
            }
            continue;
        }
        if (dial > distance) {
            dial -= distance;
        } else if (dial == distance) {
            dial = 0;
            times_pointing_at_zero += 1;
        } else {
            if (dial > 0) times_pointing_at_zero += 1;
            dial = max_dial_size - (distance - dial);
        }
    }
    return times_pointing_at_zero;
}

pub fn main() !void {
    const fst = try fstPart();
    std.debug.print("First part solution: {}\n", .{fst});
    it.reset();
    const snd = try sndPart();
    std.debug.print("Second part solution: {}\n", .{snd});
}
