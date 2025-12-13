const std = @import("std");

pub fn build(b: *std.Build) void {
    const target = b.standardTargetOptions(.{});
    const optimize = b.standardOptimizeOption(.{});
    const utils_mod = b.addModule("utils", .{
        .root_source_file = b.path("src/utils.zig"),
        .target = target,
        .optimize = optimize,
    });
    const day = b.option([]const u8, "day", "The day to run") orelse {
        std.log.err("Specify a day using -Dday=XX (e.g., -Dday=01)", .{});
        return;
    };
    const src_path = b.fmt("src/d{s}/main.zig", .{day});
    const exe_name = b.fmt("d{s}", .{day});
    const exe = b.addExecutable(.{
        .name = exe_name,
        .root_module = b.createModule(.{
            .root_source_file = b.path(src_path),
            .target = target,
            .optimize = optimize,
            .imports = &.{
                .{ .name = "utils", .module = utils_mod },
            },
        }),
    });
    b.installArtifact(exe);
    const run_cmd = b.addRunArtifact(exe);
    run_cmd.step.dependOn(b.getInstallStep());
    if (b.args) |args| {
        run_cmd.addArgs(args);
    }
    const run_step = b.step("run", "Run the selected day");
    run_step.dependOn(&run_cmd.step);
}
