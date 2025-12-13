const std = @import("std");

pub fn compose_file_split_iterator(comptime path: []const u8, delimiters: []const u8) std.mem.SplitIterator(u8, .any) {
    return std.mem.splitAny(u8, @embedFile(path), delimiters);
}
