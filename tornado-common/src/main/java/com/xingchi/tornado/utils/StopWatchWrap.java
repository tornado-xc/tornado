package com.xingchi.tornado.utils;

import org.springframework.lang.NonNull;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.text.NumberFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoya
 * @date 2023/9/14
 */
public class StopWatchWrap extends StopWatch {

    /**
     * 打印单位
     */
    private final TimeUnit printUnit;

    /**
     * 摘要信息(这个StopWatch的说明)
     */
    private final String summary;

    public StopWatchWrap() {
        this(WatchTimeUnit.MILLISECONDS);
    }

    public StopWatchWrap(WatchTimeUnit unit) {
        this("", unit);
    }

    public StopWatchWrap(String summary, WatchTimeUnit unit) {
        super();
        this.summary = summary;
        this.printUnit = Objects.isNull(unit) ? TimeUnit.MILLISECONDS : unit.getSupportUnit();
    }


    @Override
    @NonNull
    public String shortSummary() {
        String prefix = StringUtils.hasText(summary) ? summary : getId();
        return "StopWatch '" + prefix + "': running time = " + this.getTotalTime() + " " + this.getUnitString();
    }

    @Override
    @NonNull
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(shortSummary());
        sb.append('\n');
        sb.append("---------------------------------------------\n");
        sb.append(this.getUnitString()).append("         %     Task name\n");
        sb.append("---------------------------------------------\n");

        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(3);
        pf.setGroupingUsed(false);

        for (TaskInfo task : getTaskInfo()) {
            sb.append(String.format("%12s", this.getTime(task))).append("  ");
            sb.append(pf.format((double) task.getTimeNanos() / getTotalTimeNanos())).append("  ");
            sb.append(task.getTaskName()).append("\n");
        }
        return sb.toString();
    }

    /**
     * 获取单位字符串
     *
     * @return          单位str表现形式
     */
    private String getUnitString() {

        String result;
        switch (printUnit) {
            case SECONDS:
                result = "s";
                break;
            case MICROSECONDS:
                result = "μs";
                break;
            case MILLISECONDS:
                result = "ms";
                break;
            case NANOSECONDS:
            default:
                result = "ns";
        }

        return result;
    }

    /**
     * 获取单个任务的耗时
     *
     * @param task          任务id
     * @return              单个任务的耗时
     */
    private String getTime(TaskInfo task) {

        String result;
        switch (printUnit) {
            case SECONDS:
                result = String.format("%.2f", task.getTimeSeconds());
                break;
            case MICROSECONDS:
                result = String.format("%s", task.getTimeNanos() / 1000);
                break;
            case MILLISECONDS:
                result =  String.format("%s", task.getTimeMillis());
                break;
            case NANOSECONDS:
            default:
                result =  String.format("%s", task.getTimeNanos());
        }

        return result;
    }

    /**
     * 获取总耗时
     *
     * @return      总耗时
     */
    private String getTotalTime() {

        String result;
        switch (printUnit) {
            case SECONDS:
                result = String.format("%.2f", this.getTotalTimeSeconds());
                break;
            case MICROSECONDS:
                result = String.format("%s", this.getTotalTimeNanos() / 1000);
                break;
            case MILLISECONDS:
                result = String.format("%s", this.getTotalTimeMillis());
                break;
            case NANOSECONDS:
            default:
                result = String.format("%s", this.getTotalTimeNanos());
        }

        return result;
    }

}
