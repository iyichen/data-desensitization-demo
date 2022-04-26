package org.example.desensitization.log;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DesensitizationDataPatternLayout extends PatternLayout {

    private final List<Pattern> patterns = new ArrayList<>();

    public DesensitizationDataPatternLayout() {
        initPattern();
    }

    private void initPattern() {
        patterns.add(Pattern.compile("password=(.+?)[,})]")); // 密码
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        // 获取待写入文件的内容
        StringBuilder message = new StringBuilder(super.doLayout(event));
        for (Pattern pattern : patterns) {
            // 正则进行脱敏处理
            Matcher matcher = pattern.matcher(message);
            while (matcher.find()) {
                String target = matcher.group(1);
                if (StringUtils.isNotBlank(target)) {
                    for (int i = matcher.start(1), j = matcher.end(1); i < j; i++) {
                        message.setCharAt(i, '*');
                    }
                }
            }
        }
        return message.toString();
    }

}