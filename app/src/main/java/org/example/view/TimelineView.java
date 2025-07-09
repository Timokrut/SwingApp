package org.example.view;

import org.example.model.Task;
import org.example.model.TaskManager;

import javax.swing.*; 
import java.awt.*; 
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimelineView extends JPanel {
    private TaskManager taskManager;
    private int daySpan = 3;
    private LocalDateTime startDateTime = LocalDateTime.now().with(LocalTime.MIN);

    public TimelineView(TaskManager taskManager) {
        this.taskManager = taskManager;
        setPreferredSize(new Dimension(1200, 800));
        setBackground(Color.WHITE);
    }

    public void setDaySpan(int days) {
        this.daySpan = days;
        repaint();
    }

    public void shiftDays(int offset) {
        startDateTime = startDateTime.plusDays(offset);
        repaint();
    }

    public void resetToToday() {
        startDateTime = LocalDateTime.now().with(LocalTime.MIN);
        repaint();
    }

    public int getDaySpan() {
        return daySpan;
    }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTimeline(g);
    }

    private void drawTimeline(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        int hourHeight = 50;
        int headerHeight = 30;
        int totalHours = 24;
        int columnWidth = width / daySpan;

        // vertical lines
        for (int i = 0; i < daySpan; i++) {
            int x = i * columnWidth;
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(x, 0, x, height);
            g2.setColor(Color.BLACK);
            g2.drawString(startDateTime.toLocalDate().plusDays(i).toString(), x + 5, 20);
        }

        // hours at left
        for (int h = 0; h < totalHours; h++) {
            int y = headerHeight + h * hourHeight;
            g2.setColor(Color.GRAY);
            g2.drawLine(0, y, width, y);
            g2.setColor(Color.BLACK);
            g2.drawString(String.format("%02d:00", h), 5, y + 12);
        }

        // tasks
        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            drawTask(g2, task, columnWidth, hourHeight, headerHeight);
        }
    }

    private void drawTask(Graphics2D g2, Task task, int columnWidth, int hourHeight, int headerHeight) {
        LocalDateTime taskStart = task.getStartDate();
        LocalDateTime taskEnd = task.getEndDate();

        for (int i = 0; i < daySpan; i++) {
            LocalDateTime currentDay = startDateTime.plusDays(i);

            if (!taskStart.toLocalDate().isAfter(currentDay.toLocalDate()) && !taskEnd.toLocalDate().isBefore(currentDay.toLocalDate())) {
                LocalDateTime drawStart = taskStart.isBefore(currentDay.with(LocalTime.MIN)) ? currentDay.with(LocalTime.MIN) : taskEnd;
                LocalDateTime drawEnd = taskEnd.isAfter(currentDay.with(LocalTime.MAX)) ? currentDay.with(LocalTime.MAX) : taskEnd;

                long minutesFromStart = ChronoUnit.MINUTES.between(LocalTime.MIN, drawStart.toLocalTime());
                long durationMinutes = ChronoUnit.MINUTES.between(drawStart, drawEnd);

                int x = i * columnWidth + 60; 
                int y = headerHeight + (int) (minutesFromStart * hourHeight / 60);
                int height = (int) (durationMinutes * hourHeight / 60);
                int width = columnWidth - 70;

                try {
                    g2.setColor(Color.decode(task.getColor()));
                } catch (Exception e) {
                    g2.setColor(Color.CYAN);
                }

                g2.fillRoundRect(x, y, width, height, 10, 10);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(x, y, width, height, 10, 10);

                g2.setClip(x, y, width, height);
                g2.drawString(task.getTitle(), x + 5, y + 15);
                g2.setClip(null);
            }
        }
    }
}

