public class Task {
    /** Represents a Process/Task for CPU Scheduling */
    String name;
    int burst_time;
    int priority;
    int remaining_time;
    Integer start_time;
    Integer finish_time;

    public Task(String name, int burst_time, int priority) {
        // Define name, burst_time, priority, remaining_time, start_time, and finish_time
        this.name = name;
        this.burst_time = burst_time;
        this.priority = priority;
        this.remaining_time = burst_time;
        this.start_time = null;
        this.finish_time = null;
    }

    @Override
    public String toString() {
        return name + "(burst=" + burst_time + ", priority=" + priority + ")";
    }
}
