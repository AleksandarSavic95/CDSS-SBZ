package fda.sbz.cdssserver.model;

public class AlarmNotification {
    private String date;
    private String content;
    private String level;
    // TODO: Add link to log/list of logs which caused the alarm

    public AlarmNotification() {
    }

    public AlarmNotification(String date, String content, String level) {
        this.date = date;
        this.content = content;
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "AlarmNotification{" +
                "date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
