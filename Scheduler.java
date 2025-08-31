import java.util.*;

public class Scheduler {
    public List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Scheduler() {
        this.tasks = new ArrayList<>(); // List of tasks
    }

    public void get_user_tasks(Scanner scanner) {
        /** Takes user input to create Task objects */
        System.out.print("Enter the number of tasks: ");
        int num_tasks = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < num_tasks; i++) {
            System.out.print("Enter name for Task " + (i + 1) + ": ");
            String name = scanner.nextLine();
            System.out.print("Enter burst time for " + name + ": ");
            int burst_time = scanner.nextInt();
            System.out.print("Enter priority for " + name + " (lower number = higher priority): ");
            int priority = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Create Task instance and add to the tasks list
            tasks.add(new Task(name, burst_time, priority));
        }
    }

    public void reset_tasks() {
        /** Resets time variables before re-running scheduling */
        for (Task task : tasks) {
            task.remaining_time = task.burst_time;
            task.start_time = null;
            task.finish_time = null;
        }
    }

    public void fcfs() {
        /** Implements First-Come, First-Serve (FCFS) Scheduling */
        System.out.println("\nRunning First-Come, First-Serve (FCFS) Scheduling:");
        // Initialize current_time = 0
        int current_time = 0;
        // Loop through tasks and assign start_time and finish_time
        for(Task task: tasks){
            task.start_time = current_time;

            current_time += task.burst_time;

            task.finish_time = current_time;

            System.out.println(task.name + ": start at " + task.start_time + ", finish at " + task.finish_time);
        }
    }

    public void round_robin(int quantum) {
        /** Implements Round Robin Scheduling */
        System.out.println("\nRunning Round Robin Scheduling:");
        // Initialize a queue with tasks
        Queue<Task> taskQueue = new LinkedList<>(tasks);
        // Process each task in cyclic order until all tasks are completed
        int current_time = 0;

        while(!taskQueue.isEmpty()){
            Task task = taskQueue.poll();

            if(task.start_time == null){
                task.start_time = current_time;
            }

            if(task.remaining_time > quantum){
                current_time += quantum;
                task.remaining_time -= quantum;
                taskQueue.add(task);
            }else{
                current_time += task.remaining_time;
                task.remaining_time = 0;
                task.finish_time = current_time;
                System.out.println(task.name + ": start at " + task.start_time + ", finish at " + task.finish_time);
            }
        }
    }

    public void priority_scheduling() {
        /** Implements Non-Preemptive Priority Scheduling */
        System.out.println("\nRunning Priority Scheduling:");
        // Sort tasks based on priority and execute them
        tasks.sort(Comparator.comparingInt(task -> task.priority));
        int current_time = 0;

        for(Task task: tasks){
            task.start_time = current_time;
            current_time += task.burst_time;
            task.finish_time = current_time;
            System.out.println(task.name + ": start at " + task.start_time + ", finish at " + task.finish_time);
        }
    }

    public void main() {
        /** Menu system to allow user to select scheduling algorithm */
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose a Scheduling Algorithm:");
            System.out.println("1. First-Come, First-Serve (FCFS)");
            System.out.println("2. Round Robin (RR)");
            System.out.println("3. Priority Scheduling (Non-Preemptive)");
            System.out.println("4. Exit");

            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();
            reset_tasks();

            if (choice.equals("1")) {
                fcfs();
            } else if (choice.equals("2")) {
                System.out.print("Enter time quantum for Round Robin: ");
                int quantum = scanner.nextInt();
                round_robin(quantum);
            } else if (choice.equals("3")) {
                priority_scheduling();
            } else if (choice.equals("4")) {
                System.out.println("Exiting CPU Task Scheduler. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice! Please enter a valid option.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scheduler scheduler = new Scheduler();
        scheduler.get_user_tasks(scanner);
        scheduler.main();
        scanner.close();
    }
}
