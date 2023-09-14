package com.xingchi.tornado.utils;

import org.springframework.lang.NonNull;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.text.NumberFormat;
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

    public StopWatchWrap(TimeUnit printUnit) {
        this("", printUnit);
    }

    public StopWatchWrap(String summary, TimeUnit printUnit) {
        super();
        this.summary = summary;
        this.printUnit = printUnit;
    }


    @Override
    @NonNull
    public String shortSummary() {
        String prefix = StringUtils.hasText(summary) ? summary : "StopWatch '";
        return prefix + getId() + "': running time = " + this.getTotalTime() + " " + this.getUnitString();
    }

    @Override
    @NonNull
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(shortSummary());
        sb.append('\n');
        sb.append("---------------------------------------------\n");
        sb.append(this.getUnitString()).append("         %     Task name\n");
        sb.append("---------------------------------------------\n");
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(9);
        nf.setGroupingUsed(false);
        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(3);
        pf.setGroupingUsed(false);
        for (TaskInfo task : getTaskInfo()) {
            sb.append(nf.format(this.getTime(task))).append("  ");
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
    private double getTime(TaskInfo task) {

        double result;
        switch (printUnit) {
            case SECONDS:
                result = task.getTimeSeconds();
                break;
            case MILLISECONDS:
                result = task.getTimeMillis();
                break;
            case NANOSECONDS:
            default:
                result = task.getTimeNanos();
        }

        return result;
    }

    /**
     * 获取总耗时
     *
     * @return      总耗时
     */
    private double getTotalTime() {

        double result;
        switch (printUnit) {
            case SECONDS:
                result = this.getTotalTimeSeconds();
                break;
            case MILLISECONDS:
                result = this.getTotalTimeMillis();
                break;
            case NANOSECONDS:
            default:
                result = this.getTotalTimeNanos();
        }

        return result;
    }



}
