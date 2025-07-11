package org.example.view;

import org.example.model.Task;
import org.example.model.TaskManager;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;

public class TimelineView extends JPanel {
    private TaskManager taskManager;
    private int daySpan = 3;
    private LocalDateTime startDateTime = LocalDateTime.now().with(LocalTime.MIN);
    private final Map<Rectangle, Task> taskBounds = new HashMap<>();

    public TimelineView(TaskManager taskManager) {
        this.taskManager = taskManager;
        
        // TODO: Make better UI 
        ToolTipManager.sharedInstance().registerComponent(this);

        setPreferredSize(new Dimension(1200, 800));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();

                for (Map.Entry<Rectangle, Task> entry : taskBounds.entrySet()) {
                    if (entry.getKey().contains(clickPoint)) {
                        Task clickedTask = entry.getValue();

                        TaskDialog dialog = new TaskDialog(SwingUtilities.getWindowAncestor(TimelineView.this), taskManager, clickedTask);
                        dialog.setVisible(true);
                        repaint();
                        break;
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override 
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean found = false;

                for (Map.Entry<Rectangle, Task> entry : taskBounds.entrySet()) {
                    if (entry.getKey().contains(p)) {
                        Task t = entry.getValue();
                        setToolTipText(t.getDescription() != null && !t.getDescription().isBlank() ? t.getDescription() : "No description");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    setToolTipText(null);
                }
            }
        });

        resetToToday();
    }

    public void setDaySpan(int days) {
        this.daySpan = days;
        resetToToday();
    }

    public void shiftDays(int offset) {
        startDateTime = startDateTime.plusDays(offset);
        repaint();
    }

    public void resetToToday() {
        LocalDateTime today = LocalDateTime.now().with(LocalTime.MIN);

        if (daySpan == 3) {
            // Center current day
            startDateTime = today.minusDays(1);
        } else if (daySpan == 7) {
            // Start from monday
            startDateTime = today.with(java.time.DayOfWeek.MONDAY);
        } else {
            startDateTime = today;
        }

        repaint();
    }

    public int getDaySpan() {
        return daySpan;
    }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        taskBounds.clear();
        drawTimeline(g);
    }

    private void drawTimeline(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        int hourHeight = 50;
        int headerHeight = 30;
        int totalHours = 24;
        int timeLabelWidth = 50;
        int columnWidth = (width - timeLabelWidth) / daySpan;

        LocalDateTime today = LocalDateTime.now();

        // Vertical lines
        for (int i = 0; i < daySpan; i++) {
            int x = timeLabelWidth + i * columnWidth;
            LocalDateTime thisDay = startDateTime.plusDays(i);

            if (thisDay.toLocalDate().isBefore(today.toLocalDate())) {
                // -> PAST DAY
                g2.setColor(new Color(230, 230, 230));
                g2.fillRect(x, 0, columnWidth, height);
            } else if (thisDay.toLocalDate().equals(today.toLocalDate())) {
                // -> TODAY
                g2.setColor(new Color(150, 200, 255, 80));
                g2.fillRect(x, 0, columnWidth, height);
            }

            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(x, 0, x, height);
            g2.setColor(Color.BLACK);
            g2.drawString(startDateTime.toLocalDate().plusDays(i).toString(), x + 5, 20);
        }

        // Hours at left
        for (int h = 0; h < totalHours; h++) {
            int y = headerHeight + h * hourHeight;
            g2.setColor(Color.GRAY);
            g2.drawLine(timeLabelWidth, y, width, y);
            g2.setColor(Color.BLACK);
            g2.drawString(String.format("%02d:00", h), 5, y + 12);
        }

        // Tasks
        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            drawTask(g2, task, columnWidth, hourHeight, headerHeight, timeLabelWidth);
        }
    }

    private void drawTask(Graphics2D g2, Task task, int columnWidth, int hourHeight, int headerHeight, int timeLabelWidth) {
        LocalDateTime taskStart = task.getStartDate();
        LocalDateTime taskEnd = task.getEndDate();

        for (int i = 0; i < daySpan; i++) {
            LocalDateTime currentDay = startDateTime.plusDays(i);

            if (!taskStart.toLocalDate().isAfter(currentDay.toLocalDate()) && !taskEnd.toLocalDate().isBefore(currentDay.toLocalDate())) {
                LocalDateTime drawStart = taskStart.isBefore(currentDay.with(LocalTime.MIN)) ? currentDay.with(LocalTime.MIN) : taskStart;
                LocalDateTime drawEnd = taskEnd.isAfter(currentDay.with(LocalTime.MAX)) ? currentDay.with(LocalTime.MAX) : taskEnd;

                long minutesFromStart = ChronoUnit.MINUTES.between(LocalTime.MIN, drawStart.toLocalTime());
                long durationMinutes = ChronoUnit.MINUTES.between(drawStart, drawEnd);

                int x = timeLabelWidth + i * columnWidth + 10; 
                int y = headerHeight + (int) (minutesFromStart * hourHeight / 60);
                int height = (int) (durationMinutes * hourHeight / 60);
                int width = columnWidth - 20;
               
                // HitBox for MouseEvent
                Rectangle rect = new Rectangle(x, y, width, height);
                taskBounds.put(rect, task);

                try {
                    g2.setColor(Color.decode(task.getColor()));
                } catch (Exception e) {
                    g2.setColor(Color.CYAN);
                }

                g2.fillRoundRect(x, y, width, height, 10, 10);

                // Bounds 
                g2.setColor(Color.DARK_GRAY);
                g2.drawRoundRect(x, y, width, height, 10, 10);

                // text
                g2.setColor(Color.BLACK);
                g2.setClip(x, y, width, height);
                g2.drawString(task.getTitle(), x + 5, y + 15);

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                String startDateFormatted = task.getStartDate().format(dtf); 
                String endDateFormatted = task.getEndDate().format(dtf); 

                g2.drawString(startDateFormatted + " - " + endDateFormatted, x + 5, y + 30);
                g2.setClip(null);
            }
        }
    }
}

