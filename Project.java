import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {

        int[] returnArr = new int[tasks.size()];

        Map<Integer,Integer> taskDone = new HashMap<>(); // task id, ending time
        taskDone.put(tasks.get(0).getTaskID(), tasks.get(0).getDuration());
        int curTime=tasks.get(0).getDuration();
        returnArr[0]=0;

        while(taskDone.size()!= tasks.size()){

            for(int i = 1 ; i< tasks.size(); i++){

                Task task =tasks.get(i);
                if (taskDone.containsKey(task.getTaskID()))
                    continue;

                boolean check = true;
                for (int depend : task.getDependencies()){//if all dependencies are satisfied
                    if (!taskDone.containsKey(depend)){
                        check= false;
                        break;
                    }
                }

                if (check){
                    for (int depend : task.getDependencies()){// check their ending times
                        if (taskDone.get(depend)>curTime){
                            check= false;
                            break;
                        }
                    }
                }

                if (check){
                    returnArr[i]=curTime;
                    taskDone.put(task.getTaskID(),curTime+ task.getDuration());
                }
            }
            curTime++;
        }
        return returnArr;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int projectDuration = 0;

        int[] last = getEarliestSchedule();

        int lastTask =  last[last.length-1];

        projectDuration= tasks.get(tasks.size()-1).getDuration()+lastTask;

        return projectDuration;
    }

    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s", "Task ID", "Description", "Start", "End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i] + t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length - 1).getDuration() + schedule[schedule.length - 1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
