package com.livk.common.core;

import com.livk.common.core.util.DateUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * <p>
 * MicroserviceBanner
 * </p>
 *
 * @author livk
 * @date 2022/8/12
 */
@NoArgsConstructor(staticName = "create")
class MicroserviceBanner implements Banner {

    private static final String banner = """
             ████     ████ ██                                                         ██               \s
            ░██░██   ██░██░░                                                         ░░                \s
            ░██░░██ ██ ░██ ██  █████  ██████  ██████   ██████  █████  ██████ ██    ██ ██  █████   █████\s
            ░██ ░░███  ░██░██ ██░░░██░░██░░█ ██░░░░██ ██░░░░  ██░░░██░░██░░█░██   ░██░██ ██░░░██ ██░░░██
            ░██  ░░█   ░██░██░██  ░░  ░██ ░ ░██   ░██░░█████ ░███████ ░██ ░ ░░██ ░██ ░██░██  ░░ ░███████
            ░██   ░    ░██░██░██   ██ ░██   ░██   ░██ ░░░░░██░██░░░░  ░██    ░░████  ░██░██   ██░██░░░░\s
            ░██        ░██░██░░█████ ░███   ░░██████  ██████ ░░██████░███     ░░██   ░██░░█████ ░░██████
            ░░         ░░ ░░  ░░░░░  ░░░     ░░░░░░  ░░░░░░   ░░░░░░ ░░░       ░░    ░░  ░░░░░   ░░░░░░\s
            """;

    @SneakyThrows
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(banner);
        int max = Arrays.stream(banner.split("\n")).mapToInt(String::length).max().orElse(0);
        max = max % 2 == 0 ? max : max + 1;
        var format = Format.create(max, out);
        format.accept(" Spring Boot Version: " + SpringBootVersion.getVersion() + " ");
        format.accept(" Current time: " + DateUtils.format(LocalDateTime.now(), DateUtils.YMD_HMS) + " ");
        format.accept(" Current JDK Version: " + System.getProperty("java.version") + " ");
        format.accept(" Operating System: " + System.getProperty("os.name") + " ");
        out.flush();
    }

    @RequiredArgsConstructor(staticName = "create")
    private static class Format implements Consumer<String> {

        private final static char ch = '*';
        private final int n;
        private final PrintStream out;

        @Override
        public void accept(String str) {
            var length = str.length();
            if (length < n) {
                var index = (n - length) >> 1;
                str = StringUtils.leftPad(str, length + index, ch);
                str = StringUtils.rightPad(str, n, ch);
            }
            out.println(str);
        }
    }

}
