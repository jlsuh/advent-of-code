const std = @import("std");

var it = std.mem.splitAny(u8, input, "\n");
const input = @embedFile("input.txt");

pub fn fstPart() !u32 {
    var dial: i16 = 50;
    var timesLeftPointingAtZero: u32 = 0;
    while (it.next()) |rotation| {
        const distance: i16 = try std.fmt.parseInt(i16, rotation[1..], 10);
        dial += if (rotation[0] == 'R') distance else -distance;
        dial = @mod(dial, 100);
        if (dial == 0) timesLeftPointingAtZero += 1;
    }
    return timesLeftPointingAtZero;
}

pub fn sndPart() !u32 {
    return 0;
}

pub fn main() !void {
    const fst = try fstPart();
    std.debug.print("First part solution: {}\n", .{fst});
    const snd = try sndPart();
    std.debug.print("Second part solution: {}\n", .{snd});
}
